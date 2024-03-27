from PySide6.QtGui import QPen, QBrush, Qt
from PySide6.QtWidgets import QGraphicsRectItem

from settings import GRID_SPACING


class ConnectionItem(QGraphicsRectItem):
    def __init__(self, x, y, parent):
        super(ConnectionItem, self).__init__()
        self.size = GRID_SPACING
        self.setParentItem(parent)
        self.setRect(x, y, self.size, self.size)
        self.setCursor(Qt.PointingHandCursor)

        self.setPen(QPen(Qt.blue))
        self.setBrush(QBrush(Qt.white))


