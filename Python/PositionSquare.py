import PySide6.QtWidgets
from PySide6.QtGui import QFont
from PySide6.QtWidgets import QGraphicsRectItem, QGraphicsTextItem, QGraphicsEllipseItem


class PositionSquare(QGraphicsRectItem):
    def __init__(self, position_number, x, y, element, main_window, size=50):
        super().__init__(x, y, size, size)
        self.setFlag(QGraphicsRectItem.ItemIsSelectable)
        self.position_number = position_number  # Номер позиции

        self.left_x = x
        self.left_y = y

        self.main_window = main_window

        # Add text with position number
        self.position_text_item = QGraphicsTextItem(f"P{position_number}")
        font = QFont("Arial", 10)
        self.position_text_item.setFont(font)
        text_width = self.position_text_item.boundingRect().width()
        text_height = self.position_text_item.boundingRect().height()
        self.position_text_item.setPos(x, y)
        self.position_text_item.setParentItem(self)

        self.element = element  # Ссылка на элемент, связанный с этой позицией

        # Add text with element number (bottom left)
        self.element_number_text_item = QGraphicsTextItem(f"E{element.number}")
        self.element_number_text_item.setFont(font)
        self.element_number_text_item.setPos(x, y + size - text_height)
        self.element_number_text_item.setParentItem(self)

    def create_contact_points(self):
        # Создание точек контактов
        contact_point_size = 5
        for i in range(2):
            # Верхние точки
            top_point_left = QGraphicsEllipseItem(self.x() + (i + 1) * (self.rect().width() / 3) - contact_point_size / 2,
                                                   self.y() - contact_point_size / 2,
                                                   contact_point_size,
                                                   contact_point_size)
            top_point_left.setFlag(QGraphicsEllipseItem.ItemIsSelectable)
            self.main_window.scene.addItem(top_point_left)
            self.contact_points.append(top_point_left)

            top_point_right = QGraphicsEllipseItem(self.x() + (i + 1) * (self.rect().width() * 2 / 3) - contact_point_size / 2,
                                                    self.y() - contact_point_size / 2,
                                                    contact_point_size,
                                                    contact_point_size)
            top_point_right.setFlag(QGraphicsEllipseItem.ItemIsSelectable)
            self.main_window.scene.addItem(top_point_right)
            self.contact_points.append(top_point_right)

            # Нижние точки
            bottom_point_left = QGraphicsEllipseItem(self.x() + (i + 1) * (self.rect().width() / 3) - contact_point_size / 2,
                                                      self.y() + self.rect().height() - contact_point_size / 2,
                                                      contact_point_size,
                                                      contact_point_size)
            bottom_point_left.setFlag(QGraphicsEllipseItem.ItemIsSelectable)
            self.main_window.scene.addItem(bottom_point_left)
            self.contact_points.append(bottom_point_left)

            bottom_point_right = QGraphicsEllipseItem(self.x() + (i + 1) * (self.rect().width() * 2 / 3) - contact_point_size / 2,
                                                       self.y() + self.rect().height() - contact_point_size / 2,
                                                       contact_point_size,
                                                       contact_point_size)
            bottom_point_right.setFlag(QGraphicsEllipseItem.ItemIsSelectable)
            self.main_window.scene.addItem(bottom_point_right)
            self.contact_points.append(bottom_point_right)

            # Левые точки
            left_point_top = QGraphicsEllipseItem(self.x() - contact_point_size / 2,
                                                   self.y() + (i + 1) * (self.rect().height() / 3) - contact_point_size / 2,
                                                   contact_point_size,
                                                   contact_point_size)
            left_point_top.setFlag(QGraphicsEllipseItem.ItemIsSelectable)
            self.main_window.scene.addItem(left_point_top)
            self.contact_points.append(left_point_top)

            left_point_bottom = QGraphicsEllipseItem(self.x() - contact_point_size / 2,
                                                      self.y() + (i + 1) * (self.rect().height() * 2 / 3) - contact_point_size / 2,
                                                      contact_point_size,
                                                      contact_point_size)
            left_point_bottom.setFlag(QGraphicsEllipseItem.ItemIsSelectable)
            self.main_window.scene.addItem(left_point_bottom)
            self.contact_points.append(left_point_bottom)

            # Правые точки
            right_point_top = QGraphicsEllipseItem(self.x() + self.rect().width() - contact_point_size / 2,
                                                    self.y() + (i + 1) * (self.rect().height() / 3) - contact_point_size / 2,
                                                    contact_point_size,
                                                    contact_point_size)
            right_point_top.setFlag(QGraphicsEllipseItem.ItemIsSelectable)
            self.main_window.scene.addItem(right_point_top)
            self.contact_points.append(right_point_top)

            right_point_bottom = QGraphicsEllipseItem(self.x() + self.rect().width() - contact_point_size / 2,
                                                       self.y() + (i + 1) * (self.rect().height() * 2 / 3) - contact_point_size / 2,
                                                       contact_point_size,
                                                       contact_point_size)
            right_point_bottom.setFlag(QGraphicsEllipseItem.ItemIsSelectable)
            self.main_window.scene.addItem(right_point_bottom)
            self.contact_points.append(right_point_bottom)

    def mousePressEvent(self, event: PySide6.QtWidgets.QGraphicsSceneMouseEvent) -> None:
        self.main_window.create_connection(self)


