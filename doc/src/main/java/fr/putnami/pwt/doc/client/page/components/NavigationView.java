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
package fr.putnami.pwt.doc.client.page.components;

import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;

import fr.putnami.pwt.core.inject.client.annotation.PostConstruct;
import fr.putnami.pwt.core.inject.client.annotation.Templated;
import fr.putnami.pwt.core.mvp.client.View;
import fr.putnami.pwt.core.widget.client.HTMLPanel;
import fr.putnami.pwt.core.widget.client.NavSpy;

@Templated
public class NavigationView extends Composite implements View {

	@UiField
	NavSpy sampleNavSpy;

	@UiField
	HTMLPanel scrollTarget;

	@PostConstruct
	public void postConstruct() {
		sampleNavSpy.redraw();
	}

}
