from flask import Flask, render_template, request, jsonify, session
from game import Game

app = Flask(__name__)
app.secret_key = "change-this-before-deploying"  # needed to sign session cookies


@app.route("/")
def index():
    game = Game()
    state = game.start()
    session["game"] = game.to_dict()
    return render_template("index.html", state=state)


@app.route("/action", methods=["POST"])
def action():
    if "game" not in session:
        return jsonify({"error": "No active game. Please refresh to start a new one."}), 400

    data = request.get_json()
    choice = data.get("choice")

    game = Game.from_dict(session["game"])
    state = game.action(choice)
    session["game"] = game.to_dict()

    return jsonify(state)


if __name__ == "__main__":
    app.run(debug=True)
