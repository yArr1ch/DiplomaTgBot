import csv

from flask import Flask, request, jsonify
import pandas as pd
from sklearn.ensemble import RandomForestRegressor, RandomForestClassifier
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split

app = Flask(__name__)


def parse_into_csv(received_data, file_name):
    rows = received_data.strip().split('\n')

    data = [row.split('\t') for row in rows]

    csv_file_path = f'{file_name}.csv'

    with open(csv_file_path, mode='w', newline='') as file:
        writer = csv.writer(file)

        writer.writerow(data[0])

        max_cols = max(len(row) for row in data)

        for row in data[1:]:
            row += [''] * (max_cols - len(row))
            writer.writerow(row)

        print("CSV file created successfully.")

    with open(csv_file_path, mode='r', newline='') as file:
        reader = csv.reader(file)
        rows = [row[:-1] for row in reader]

    with open(csv_file_path, mode='w', newline='') as file:
        writer = csv.writer(file)
        writer.writerows(rows)

    print("Anonymous column removed from the CSV file.")


@app.route('/predict', methods=['POST'])
def predict():
    if request.method == 'POST':
        string_data = request.data.decode('utf-8')
        parse_into_csv(string_data, "data_for_prediction")
        print(string_data)

        data = pd.read_csv("data_for_prediction.csv")

        data.dropna(inplace=True)

        X = data[['Open', 'High', 'Low', 'Close']]
        y = data['Adj Close']

        model = RandomForestRegressor(n_estimators=100, random_state=42)
        model.fit(X, y)

        features_for_prediction = X.tail(1)
        predicted_close_price = model.predict(features_for_prediction)
        print(predicted_close_price[0])

        return jsonify({'predicted_close_price': predicted_close_price[0]}), 200


@app.route('/analyze', methods=['POST'])
def suggest_buy_or_sell():
    if request.method == 'POST':
        string_data = request.data.decode('utf-8')
        parse_into_csv(string_data, "data_for_analyze")

        data = pd.read_csv("data_for_analyze.csv")
        data.dropna(inplace=True)

        data['Price Change'] = data['Close'] - data['Open']
        data['Buy or Sell'] = (data['Price Change'] > 0).astype(int)  # 1 for buy, 0 for sell

        X = data[['Open', 'High', 'Low']]
        y = data['Buy or Sell']

        X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

        model = RandomForestClassifier(n_estimators=100, random_state=42)

        model.fit(X_train, y_train)

        y_pred = model.predict(X_test)

        accuracy = accuracy_score(y_test, y_pred)

        features_for_prediction = X.tail(1)
        prediction = model.predict(features_for_prediction)[0]
        print(prediction)

        return jsonify({
            'accuracy': accuracy,
            'prediction': 'Buy' if prediction == 1 else 'Sell'}), 200


if __name__ == '__main__':
    app.run(debug=True)
