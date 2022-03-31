import json

from flask import Flask, Response, make_response, jsonify, request
from ModelDriver import *

app = Flask(__name__)


@app.route('/health')
def health():
    result = {'status': 'UP'}
    return Response(json.dumps(result), mimetype='application/json')


@app.route('/getUser')
def get_user():
    return Response(json.dumps({'username': 'python', 'password': 'python'}),
                    mimetype='application/json')


@app.route('/model/run', methods=["post"])
def run_model():
    print("Running started.")
    print(request.json.get("kwargs"))
    predictions = ModelDriver.run_model_on_data(request.json.get("model_type"),
                                                request.json.get("data").replace('\\', '/'),
                                                request.json.get("configs"),
                                                request.json.get("model_path").replace('\\', '/'),
                                                request.json.get("model_root").replace('\\', '/'),
                                                request.json.get("labels_path").replace('\\', '/'),
                                                request.json.get("model_version"),
                                                request.json.get("params"))

    print("Running finished.")
    print(predictions)
    return Response(json.dumps(predictions), mimetype='application/json')


@app.route('/model/train', methods=["post"])
def train_model():
    print("Training started.")
    print(request.json)
    data_list = request.json.get("data_list")
    annotation_list = request.json.get("annotation_list")
    print(data_list)
    print(annotation_list)
    accuracy = ModelDriver.train_model_on_data_set(request.json.get("model_type"),
                                                   request.json.get("data_list"),
                                                   request.json.get("annotation_list"),
                                                   request.json.get("model_path").replace('\\', '/'),
                                                   request.json.get("model_root").replace('\\', '/'),
                                                   request.json.get("labels_path").replace('\\', '/'),
                                                   request.json.get("save_path").replace('\\', '/'),
                                                   request.json.get("params"))
    print("Training finished.")
    print("Accuracy: " + str(accuracy))
    return make_response(accuracy)


@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({'error': 'Not found'}), 404)


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=6000)
