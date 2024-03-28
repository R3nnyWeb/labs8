from PySide6.QtGui import QPen, QBrush, Qt
from PySide6.QtWidgets import QGraphicsRectItem, QGraphicsItem

from Connection import Connection
from settings import GRID_SPACING


class ConnectionItem(QGraphicsRectItem):
    def __init__(self, x, y, parent):
        super(ConnectionItem, self).__init__()
        self.size = GRID_SPACING
        self.setParentItem(parent)
        self.setRect(x, y, self.size, self.size)
        self.setCursor(Qt.PointingHandCursor)

        self.setFlag(QGraphicsItem.ItemIsSelectable)  # Добавить этот флаг

        self.setPen(QPen(Qt.blue))
        self.setBrush(QBrush(Qt.white))

        self.connection = None

    def mousePressEvent(self, event):
        if event.button() == Qt.LeftButton:
            if self.connection is None:
                self.connection = Connection(event.scenePos(), self.scene())
                print("connection")
        elif event.button() == Qt.RightButton:
            self.connection.delete_connection()
            self.connection = None
        super().mousePressEvent(event)


    def mouseReleaseEvent(self, event):
        print("super().mouseReleaseEvent(event)")
        super().mouseReleaseEvent(event)
