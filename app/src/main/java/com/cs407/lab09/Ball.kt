package com.cs407.lab09

/**
 * Represents a ball that can move. (No Android UI imports!)
 *
 * Constructor parameters:
 * - backgroundWidth: the width of the background, of type Float
 * - backgroundHeight: the height of the background, of type Float
 * - ballSize: the width/height of the ball, of type Float
 */
class Ball(
    private val backgroundWidth: Float,
    private val backgroundHeight: Float,
    private val ballSize: Float
) {
    var posX = 0f
    var posY = 0f
    var velocityX = 0f
    var velocityY = 0f
    private var accX = 0f
    private var accY = 0f

    private var isFirstUpdate = true

    init {
        reset()
    }

    /**
     * Updates the ball's position and velocity based on the given acceleration and time step.
     * (See lab handout for physics equations)
     */
    fun updatePositionAndVelocity(xAcc: Float, yAcc: Float, dT: Float) {
        if(isFirstUpdate) {
            isFirstUpdate = false
            accX = xAcc
            accY = yAcc
            return
        }

        if (dT <= 0f) return

        // Previous accelerations
        val a0x = accX
        val a0y = accY

        // v1 = v0 + 0.5*(a1 + a0)*dt
        val newVx = velocityX + 0.5f * (xAcc + a0x) * dT
        val newVy = velocityY + 0.5f * (yAcc + a0y) * dT

        // l = v0*dt + (dt^2)/6 * (3*a0 + a1)
        val dt2 = dT * dT
        val dx = velocityX * dT + (dt2 / 6f) * (3f * a0x + xAcc)
        val dy = velocityY * dT + (dt2 / 6f) * (3f * a0y + yAcc)

        // Update positions
        posX += dx
        posY += dy

        // Update velocities
        velocityX = newVx
        velocityY = newVy

        // Store latest accelerations for next step
        accX = xAcc
        accY = yAcc

        // Ensure ball stays within the field
        checkBoundaries()
    }

    /**
     * Ensures the ball does not move outside the boundaries.
     * When it collides, velocity and acceleration perpendicular to the
     * boundary should be set to 0.
     */
    fun checkBoundaries() {
        // Left wall
        if (posX < 0f) {
            posX = 0f
            velocityX = 0f
            accX = 0f
        }

        // Right wall
        val maxX = backgroundWidth - ballSize
        if (posX > maxX) {
            posX = maxX
            velocityX = 0f
            accX = 0f
        }

        // Top wall
        if (posY < 0f) {
            posY = 0f
            velocityY = 0f
            accY = 0f
        }

        // Bottom wall
        val maxY = backgroundHeight - ballSize
        if (posY > maxY) {
            posY = maxY
            velocityY = 0f
            accY = 0f
        }
    }

    /**
     * Resets the ball to the center of the screen with zero
     * velocity and acceleration.
     */
    fun reset() {
        // Center the ball in the field and zero motion state
        posX = (backgroundWidth - ballSize) / 2f
        posY = (backgroundHeight - ballSize) / 2f
        velocityX = 0f
        velocityY = 0f
        accX = 0f
        accY = 0f
        isFirstUpdate = true
    }
}