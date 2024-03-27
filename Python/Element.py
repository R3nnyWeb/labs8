from PositionSquare import PositionSquare


class Element:
    def __init__(self, number):
        self.position = None
        self.number = number

    def set_position(self, position: PositionSquare):
        self.position = position
