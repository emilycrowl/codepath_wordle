package com.example.wordle

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var wordToGuess: String
    private var remainingGuesses = 3

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        val editText: EditText = findViewById(R.id.tvInput)
        val btnGuess: Button = findViewById(R.id.btnGuess)

        val tvGuess1: TextView = findViewById(R.id.tvGuess1)
        val tvGuess1Chk: TextView = findViewById(R.id.tvGuess1Chk)

        val tvGuess2: TextView = findViewById(R.id.tvGuess2)
        val tvGuess2Chk: TextView = findViewById(R.id.tvGuess2Chk)

        val tvGuess3: TextView = findViewById(R.id.tvGuess3)
        val tvGuess3Check: TextView = findViewById(R.id.tvGuess3Check)

        val tvCorrect: TextView = findViewById(R.id.tvCorrect)

        // Get a random word to guess
        wordToGuess = FourLetterWordList.getRandomFourLetterWord()
        Log.d("MainActivity", "word: $wordToGuess") // Debugging line

        // Set up the click listener
        btnGuess.setOnClickListener {
            if (remainingGuesses > 0) {
                var guess = editText.text.toString().uppercase()

                // Check if the guess length matches the word length
                if (guess.length != wordToGuess.length) {
                    Toast.makeText(this, "Please enter a ${wordToGuess.length}-letter word.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val result = checkGuess(guess)
                when (remainingGuesses) {
                    3 -> {
                        tvGuess1.text = "Guess #1: $guess"
                        tvGuess1Chk.text = result
                    }
                    2 -> {
                        tvGuess2.text = "Guess #2: $guess"
                        tvGuess2Chk.text = result
                    }
                    1 -> {
                        tvGuess3.text = "Guess #3: $guess"
                        tvGuess3Check.text = result
                    }
                }

                // Check if the user has guessed the correct word
                if (result == "OOOO") {
                    Toast.makeText(this, "Congratulations! You've guessed the word!", Toast.LENGTH_LONG).show()
                    btnGuess.isEnabled = false
                    return@setOnClickListener
                }

                remainingGuesses--

                if (remainingGuesses == 0) {
                    tvCorrect.text = "The correct word was: $wordToGuess"
                    Toast.makeText(this, "Your 3 guesses are up!", Toast.LENGTH_LONG).show()
                    btnGuess.isEnabled = false // Disable the button after 3 guesses
                }

                // Clear the input field for the next guess
                editText.text.clear()
            }
        }
    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String): String {
        var result = ""
        for (i in guess.indices) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            } else if (guess[i] in wordToGuess) {
                result += "+"
            } else {
                result += "X"
            }
        }
        Log.d("MainActivity", "result: $result")
        return result
    }
}
