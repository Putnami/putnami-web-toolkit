/**
 * This file is part of pwt.
 *
 * pwt is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * pwt is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with pwt. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package fr.putnami.pwt.core.inject.rebind.delegate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import fr.putnami.pwt.core.inject.rebind.base.InjectorCreatorDelegate;
import fr.putnami.pwt.core.inject.rebind.base.InjectorWritterConstructor;
import fr.putnami.pwt.core.inject.rebind.base.InjectorWritterInit;
import fr.putnami.pwt.core.inject.rebind.base.InjectorWritterStatic;
import fr.putnami.pwt.core.service.client.ServiceProxy;

public class InjectServiceCreator extends InjectorCreatorDelegate
	implements InjectorWritterStatic, InjectorWritterInit, InjectorWritterConstructor {

	private final JType viewType;
	private final JField serviceField;
	private final String serviceName;
	private final String proxyTypeName;
	private boolean declareProxy = true;

	public InjectServiceCreator(JType viewType, JField serviceField) {
		this.viewType = viewType;
		this.serviceField = serviceField;

		this.serviceName = serviceField.getType().getQualifiedSourceName();

		Class fieldClass;
		try {
			fieldClass = this.getClass().getClassLoader().loadClass(serviceField.getType().getQualifiedBinaryName());
			if (ServiceProxy.class.isAssignableFrom(fieldClass)) {
				this.declareProxy = false;
				this.proxyTypeName = serviceField.getType().getQualifiedSourceName();
			} else {
				this.proxyTypeName = "_" + serviceField.getName() + "ServiceProxy";
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void initComposer(ClassSourceFileComposerFactory composerFactory) {
		composerFactory.addImport(GWT.class.getName());
		composerFactory.addImport(ServiceProxy.class.getName());
		composerFactory.addImport(this.serviceName);
	}

	@Override
	public void writeStatic(SourceWriter srcWriter) {
		if (this.declareProxy) {
			srcWriter.println("interface %s extends ServiceProxy<%s_Injector, %s>, %s {}",
				this.proxyTypeName, this.viewType.getSimpleSourceName(), this.serviceName, this.serviceName);
		}
	}

	@Override
	public void writeConstructor(SourceWriter srcWriter) {
		srcWriter.println("%s = (%s) GWT.create(%s.class);",
			this.serviceField.getName(), this.proxyTypeName, this.proxyTypeName);
		srcWriter.println("((%s)%s).bindService(this);", this.proxyTypeName, this.serviceField.getName());
	}
}
