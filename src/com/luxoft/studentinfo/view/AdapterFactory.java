package com.luxoft.studentinfo.view;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.luxoft.studentinfo.Application;
import com.luxoft.studentinfo.model.Entry;
import com.luxoft.studentinfo.model.Folder;
import com.luxoft.studentinfo.model.Group;
import com.luxoft.studentinfo.model.Student;
import com.luxoft.studentinfo.util.IImageKeys;

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
			return ((Group) o).getEntries().toArray();
		}
	};
	
	private IWorkbenchAdapter folderAdapter = new IWorkbenchAdapter() {

		@Override
		public Object getParent(Object o) {
			return ((Folder) o).getParent();
		}

		@Override
		public String getLabel(Object o) {
			Folder folder = (Folder) o;
			return folder.getName();
		}

		@Override
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.FOLDER);
		}

		@Override
		public Object[] getChildren(Object o) {
			return ((Folder) o).getEntries().toArray();
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
		if (adapterType == IWorkbenchAdapter.class && adaptableObject instanceof Folder) {
			return folderAdapter;
		}
		return null;
	}

	@Override
	public Class[] getAdapterList() {
		return new Class[] { IWorkbenchAdapter.class };
	}

}
