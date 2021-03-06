'''
Author: Yujie Chen (scyyc7@nottingham.edu.cn)
-----
Last Modified: Saturday, 26th March 2022 18:56
Modified By: Leyang Hu (scylh6@nottingham.edu.cn)
-----
Brief: Flask app class
'''

from flask import Flask, Response, make_response, jsonify, request
from ModelDriver import *
import os
import sys

if getattr(sys, 'frozen', False):
    template_folder = os.path.join(sys._MEIPASS, 'templates')
    static_folder = os.path.join(sys._MEIPASS, 'static')
    model_folder = os.path.join(sys._MEIPASS, 'Models')
    app = Flask(__name__, template_folder=template_folder, static_folder=static_folder)
else:
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

    # print(request.json.get("params"))

    predictions = ModelDriver.run_model_on_data(request.json.get("script_type"),
                                                request.json.get("data").replace('\\', '/'),
                                                request.json.get("configs"),
                                                request.json.get("model_path").replace('\\', '/'),
                                                request.json.get("model_root").replace('\\', '/'),
                                                request.json.get("labels_path").replace('\\', '/'),
                                                request.json.get("model_version"),
                                                request.json.get("params"))

    print("Running finished.")
    # print(predictions)
    return Response(json.dumps(predictions), mimetype='application/json')


@app.route('/model/train', methods=["post"])
def train_model():
    print("Training started.")
    # print(request.json)
    # data_list = request.json.get("data_list")
    # annotation_list = request.json.get("annotation_list")
    # print(data_list)
    # print(annotation_list)
    # print(request.json.get("params"))
    accuracy, trainNum = ModelDriver.train_model_on_data_set(request.json.get("script_type"),
                                                             request.json.get("data_list"),
                                                             request.json.get("annotation_list"),
                                                             request.json.get("model_path").replace('\\', '/'),
                                                             request.json.get("model_root").replace('\\', '/'),
                                                             request.json.get("labels_path").replace('\\', '/'),
                                                             request.json.get("params"))
    print("Training finished.")
    print("Accuracy: " + str(accuracy))
    return Response(json.dumps({'accuracy': accuracy, 'trainNum': trainNum}),
                    mimetype='application/json')


@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({'error': 'Not found'}), 404)


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=6000)
