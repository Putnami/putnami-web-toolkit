/**
 * This file is part of pwt.
 *
 * pwt is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * pwt is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with pwt.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.putnami.pwt.core.widget.client.base;

import fr.putnami.pwt.core.model.client.base.HasReadonly;
import fr.putnami.pwt.core.widget.client.base.AbstractTableColumn.ColumnVisibility;
import fr.putnami.pwt.core.widget.client.base.AbstractTableColumn.Type;
import fr.putnami.pwt.core.widget.client.util.StyleUtils;

public abstract class AbstractTableCell<T> extends AbstractPanel implements
HasReadonly {

	private Type type = Type.DEFAULT;
	private Boolean readonly;
	private ColumnVisibility readonlyVisibility = ColumnVisibility.VISIBLE;

	public AbstractTableCell(String tagName) {
		super(tagName);
	}

	protected AbstractTableCell(AbstractTableCell<T> source) {
		super(source);
		readonly = source.readonly;
	}

	@Override
	public Boolean getReadonly() {
		return readonly;
	}

	@Override
	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
		renderVisible();
	}

	public ColumnVisibility getReadonlyVisibility() {
		return readonlyVisibility;
	}

	public void setReadonlyVisibility(ColumnVisibility readonlyVisibility) {
		this.readonlyVisibility = readonlyVisibility;
		renderVisible();
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
		StyleUtils.addStyle(this, type);
	}

	private void renderVisible() {
		switch (readonlyVisibility) {
		case VISIBLE:
			setVisible(true);
			break;
		case HIDE:
			setVisible(false);
			break;
		case HIDE_READONLY:
			setVisible(!Boolean.TRUE.equals(readonly));
			break;
		case VISIBLE_READONLY:
			setVisible(Boolean.TRUE.equals(readonly));
			break;
		}
	}
}
