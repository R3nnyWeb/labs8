from PySide6.QtCore import QPointF, QRectF
from PySide6.QtGui import QPainterPath, QPen, Qt
from PySide6.QtWidgets import QGraphicsPathItem

from settings import rounded


class Connection(QGraphicsPathItem):
    def __init__(self, startPos, scene):
        self.path = None
        self.startPos = QPointF(rounded(startPos.x()), rounded(startPos.y()))
        super(Connection, self).__init__()

        self.scene = scene
        self.setPen(QPen(Qt.red, 1.75))
        self.scene.addItem(self)

    def delete_connection(self):
        self.scene.removeItem(self)
        self.scene.update()
