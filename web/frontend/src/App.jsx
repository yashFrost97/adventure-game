import { useState } from 'react'
import './App.css'

function GamePhase({phase}) {
  const labels = {
    enemy: "⚔ Enemy Encounter",
    boss: "💀 Boss Fight",
    victory: "🏆 Victory",
    game_over: "☠ Game Over",
  }
  return (
    <div className='stats'>
      <p>{labels[phase] ?? phase}</p>
    </div>
  )
}

function PlayerStats({ player }) {
  return (
    <div className='stats'>
      <p>Your HP: {player.health} / {player.max_health}</p>
      <p>Potions Available: {player.potions}</p>
    </div>
  )
}

function EnemyStats({ enemy }) {
  if (!enemy.name) return null
  return (
    <div className='stats'>
      <p>Enemy: {enemy.name}</p>
      <p>Enemy HP: {enemy.health} / {enemy.max_health}</p>
    </div>
  )
}

function MessageComponent({ messages }) {
  return (
    <div className='messages'>
      <h3>Messages:</h3>
        {messages.map((msg, index) => (
          <p key={index}>{msg}</p>
        ))}
    </div>
  )
}

function ActionButtons({ onAction, phase }) {
  return (
    <div className='actions'>
      <button onClick={() => onAction("1")} className='btn'>1. Attack</button>
      <button onClick={() => onAction("2")} className='btn'>2. Drink Potion</button>
      {phase !== "boss" && <button onClick={() => onAction("3")} className='btn'>3. Flee!</button>}
      
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
    <div id="game">
      {gameState === null ? (
        <button onClick={startGame} className='btn'> Start The Game! </button>
      ) : (
        <div>
          <h1>Welcome to TexAd Dungeon!</h1>
          <GamePhase phase={gameState.phase} />
          <PlayerStats player={gameState.player} />
          <EnemyStats enemy={gameState.enemy} />
          <MessageComponent messages={gameState.messages} />
          {
            gameState.phase === "enemy" || gameState.phase === "boss" ?
            <ActionButtons onAction={sendAction} phase={gameState.phase} />  
            : <div className='stats'>
                <p>{gameState.phase === "victory" ? "You have saved Shadow's Watch! 🏆" : "Your soul has been claimed... ☠"}</p>
                <button onClick={startGame} className='btn'> Start The Game! </button>
              </div>
          }
          
        </div>
      )
    }
    </div>
  )
}

export default App
