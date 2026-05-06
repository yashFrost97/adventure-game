import { useState } from 'react'

function PlayerStats({ player }) {
  return (
    <div>
      <p>Your HP: {player.health} / {player.max_health}</p>
      <p>Potions Available: {player.potions}</p>
    </div>
  )
}

function EnemyStats({ enemy }) {
  // add a null check for the enemy
  return (
    <div>
      <p>Enemy: {enemy.name}</p>
      <p>Enemy HP: {enemy.health} / {enemy.max_health}</p>
    </div>
  )
}

function MessageComponent({ messages }) {
  return (
    <div>
      <h3>Messages:</h3>
        {messages.map((msg, index) => (
          <p key={index}>{msg}</p>
        ))}
    </div>
  )
}

function ActionButtons({ onAction }) {
  return (
    <div>
      <button onClick={() => onAction("1")}>1. Attack</button>
      <button onClick={() => onAction("2")}>2. Drink Potion</button>
      <button onClick={() => onAction("3")}>3. Flee!</button>
    </div>
  )
}

function App() {
  const [gameState, setGameState] = useState(null)

  async function startGame() {
    const response = await fetch('http://localhost:5000/start', {
      credentials: 'include'
    })
    const data = await response.json()
    console.log(data)
    setGameState(data)
  }

  async function sendAction(choice) {
    const response = await fetch('http://localhost:5000/action', {
      method: 'POST',
      credentials: 'include',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ choice })
    })
    const data = await response.json()
    setGameState(data)
  }

  return (
    <div>
      {gameState === null ? (
        <button onClick={startGame}> Start The Game! </button>
      ) : (
        <div>
          <h1>Welcome to TexAd Dungeon!</h1>
          <p>Phase: {gameState.phase}</p>
          <PlayerStats player={gameState.player} />
          <EnemyStats enemy={gameState.enemy} />
          <MessageComponent messages={gameState.messages} />
          <ActionButtons onAction={sendAction} />
        </div>
      )
    }
    </div>
  )
}

export default App
