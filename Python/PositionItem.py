from PySide6.QtCore import QPointF, QRectF
from PySide6.QtGui import QColor, QPen, QBrush, Qt, QFont
from PySide6.QtWidgets import QGraphicsItem, QGraphicsTextItem, QGraphicsRectItem

from ConnectorItem import ConnectionItem
from settings import GRID_SPACING, POSITION_SIZE


class PositionItem(QGraphicsRectItem):
    def __init__(self, x, y, number, element):
        super(PositionItem, self).__init__()
        self.grid_spacing = GRID_SPACING
        self.size = POSITION_SIZE * self.grid_spacing
        self.setFlag(QGraphicsItem.ItemIsMovable)
        self.setFlag(QGraphicsItem.ItemSendsGeometryChanges)
        self.setCursor(Qt.SizeAllCursor)

        self.number = number,
        self.element = element

        # Определяем начальные координаты кратные GRID_SIZE
        self.x = round(x / self.grid_spacing) * self.grid_spacing
        self.y = round(y / self.grid_spacing) * self.grid_spacing

        self.setRect(self.x, self.y, self.size, self.size)

        self.setPen(QPen(Qt.black))
        self.setBrush(QBrush(Qt.white))

        self.position_text_item = QGraphicsTextItem(f"P{number}")
        font = QFont("Arial", 10)
        self.position_text_item.setFont(font)
        text_height = self.position_text_item.boundingRect().height()
        self.position_text_item.setPos(self.x, self.y)
        self.position_text_item.setParentItem(self)

        self.element = element  # Ссылка на элемент, связанный с этой позицией

        # Add text with element number (bottom left)
        self.element_number_text_item = QGraphicsTextItem(f"E{number}")  # todo:Element number
        self.element_number_text_item.setFont(font)
        self.element_number_text_item.setPos(self.x, self.y + self.size - text_height)
        self.element_number_text_item.setParentItem(self)

        self.connections = []
        connection = ConnectionItem(self.x - GRID_SPACING, self.y + 0.5 * GRID_SPACING, self)
        self.childItems().append(connection)

    def itemChange(self, change, value):
        if change == QGraphicsItem.GraphicsItemChange.ItemPositionChange:
            # Перехватываем изменение координат объекта и выравниваем их по сетке
            new_pos = value
            new_x = round(new_pos.x() / self.grid_spacing) * self.grid_spacing
            new_y = round(new_pos.y() / self.grid_spacing) * self.grid_spacing
            return QPointF(new_x, new_y)
        return super().itemChange(change, value)
