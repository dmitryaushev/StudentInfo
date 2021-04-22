package com.luxoft.studentinfo.model;

import com.luxoft.studentinfo.FileManager;

public class ModelManager {

	private static ModelManager modelManager;
	private StateModel stateModel;
	
	public static ModelManager getInstance() {
		if (modelManager == null) {
			modelManager = new ModelManager();
		}
		return modelManager;
	}
	
	public StateModel getStateModel() {
		if (stateModel == null) {
			stateModel = new StateModel();
			initStateModel();
		}
		return stateModel;
	}

	private void initStateModel() {
		Group root = FileManager.populate();
		stateModel.setGroup(root);
	}
}
