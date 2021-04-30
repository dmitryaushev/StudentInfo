package com.luxoft.studentinfo.model;

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
		}
		return stateModel;
	}

}
