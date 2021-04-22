package com.luxoft.studentinfo;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.luxoft.studentinfo.model.Group;
import com.luxoft.studentinfo.model.Student;

public class AdapterFactory implements IAdapterFactory {

	private IWorkbenchAdapter groupAdapter = new IWorkbenchAdapter() {

		@Override
		public Object getParent(Object o) {
			return ((Group) o).getParent();
		}

		@Override
		public String getLabel(Object o) {
			Group group = (Group) o;
			return group.getName();
		}

		@Override
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.FOLDER);
		}

		@Override
		public Object[] getChildren(Object o) {
			return ((Group) o).getEntries();
		}
	};

	private IWorkbenchAdapter entryAdapter = new IWorkbenchAdapter() {

		@Override
		public Object getParent(Object o) {
			return ((Student) o).getParent();
		}

		@Override
		public String getLabel(Object o) {
			Student student = (Student) o;
			return student.getName();
		}

		@Override
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.FILE);
		}

		@Override
		public Object[] getChildren(Object o) {
			return new Object[0];
		}
	};

	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType == IWorkbenchAdapter.class && adaptableObject instanceof Group) {
			return groupAdapter;
		}
		if (adapterType == IWorkbenchAdapter.class && adaptableObject instanceof Student) {
			return entryAdapter;
		}
		return null;
	}

	@Override
	public Class[] getAdapterList() {
		return new Class[] { IWorkbenchAdapter.class };
	}

}
