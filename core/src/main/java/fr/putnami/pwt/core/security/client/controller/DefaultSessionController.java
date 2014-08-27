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
package fr.putnami.pwt.core.security.client.controller;

import com.google.gwt.core.client.GWT;

import fr.putnami.pwt.core.security.shared.domain.SessionDto;
import fr.putnami.pwt.core.security.shared.service.SessionService;
import fr.putnami.pwt.core.service.client.ServiceProxy;
import fr.putnami.pwt.core.service.client.annotation.AsyncHandler;

public class DefaultSessionController extends SessionController {

	interface SessionServiceRemote extends ServiceProxy<DefaultSessionController, SessionService>, SessionService {
	}

	private final SessionServiceRemote service = (SessionServiceRemote) GWT.create(SessionServiceRemote.class);

	public DefaultSessionController() {
		service.bindService(this);
	}

	@Override
	public void loadSession() {
		service.getCurrentSession();
	}

	@Override
	public void signOut() {
		service.signOut();
	}

	@AsyncHandler
	void onGetCurrentSession(SessionDto session) {
		setSession(session);
	}

	@AsyncHandler
	void onSignOut(SessionDto session) {
		setSession(session);
		fireSignOut();
	}

}
