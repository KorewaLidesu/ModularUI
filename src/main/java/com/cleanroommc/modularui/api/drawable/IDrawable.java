package com.cleanroommc.modularui.api.drawable;

import com.cleanroommc.modularui.drawable.Icon;
import com.cleanroommc.modularui.screen.viewport.GuiContext;
import com.cleanroommc.modularui.widget.Widget;
import com.cleanroommc.modularui.widget.sizer.Area;
import com.google.gson.JsonObject;

/**
 * An object which can be drawn. This is mainly used for backgrounds in {@link com.cleanroommc.modularui.api.widget.IWidget}.
 */
public interface IDrawable {

    /**
     * Draws this drawable at the given position with the given size.
     *
     * @param x      x position
     * @param y      y position
     * @param width  width
     * @param height height
     */
    void draw(int x, int y, int width, int height);

    default void draw(int width, int height) {
        draw(0, 0, width, height);
    }

    /**
     * Draws this drawable in a given area.
     *
     * @param area area
     */
    default void draw(Area area) {
        draw(area.x, area.y, area.width, area.height);
    }

    default void drawAtZero(Area area) {
        draw(0, 0, area.width, area.height);
    }

    /**
     * @return a widget with this drawable as a background
     */
    default Widget<?> asWidget() {
        return new DrawableWidget(this);
    }

    /**
     * @return this drawable as an icon
     */
    default Icon asIcon() {
        return new Icon(this);
    }

    default void loadFromJson(JsonObject json) {
    }

    IDrawable EMPTY = (x, y, width, height) -> {};

    class DrawableWidget extends Widget<DrawableWidget> {

        private final IDrawable drawable;

        public DrawableWidget(IDrawable drawable) {
            this.drawable = drawable;
        }

        @Override
        public void draw(GuiContext context) {
            this.drawable.drawAtZero(getArea());
        }
    }
}
