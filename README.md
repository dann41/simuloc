simuloc
=======

[![Build Status](https://travis-ci.org/dann41/simuloc.svg?branch=develop)](https://travis-ci.org/dann41/simuloc)

[![codecov.io](https://codecov.io/github/dann41/simuloc/coverage.svg?branch=develop)](https://codecov.io/github/dann41/simuloc?branch=develop)

[![Issue Count](https://codeclimate.com/github/dann41/simuloc/badges/issue_count.svg)](https://codeclimate.com/github/dann41/simuloc)


GPS track simulation


# Ubiquituous language

## Trip
list of stepCalculators to get form position A to position B
a trip must contain one or more stepCalculators
duration of a trip is the sum of the duration of all its stepCalculators
travelled distance of a trip is the sum of the distance of all stepCalculators of type moving
trip goes from start position of first stepCalculator to last position of last stepCalculator

## Step
description of what the tracking device does as part of a trip
it must have a starting position A and an end position B
it must have a duration
it must have a position frequency (in positions/minute) where the calculated position will take place
average speed will be calculated based in distance between A and B and duration

## Straight stepCalculator
It goes from A to B drawing a straight line

## Standstill
It stays in a given position for a period of time

## Sinusoidal stepCalculator
It goes from A to B drawing a sinusoidal movement

## Route stepCalculator
It goes form A to B following map directions

## Tracking device
Device that can listen for tracked positions for a trip

# Position
geographic coordinates (latitude, longitude, altitude)
it can contain an address name

# Tracked position
A position generated within a stepCalculator for a given time
It can contain other attributes like speed, bearing, acceleration, ...

# Trip replay
Mechanism to emit fake tracked positions in real time
Sends the emitted positions to trip listeners
