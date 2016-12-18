package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.io.Serializable;
import java.rmi.RemoteException;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

/**
 * A class type that stores Quality surveys 
 */

public class CtQualitySurvey implements Serializable, JIntIs {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;
	
	/** The id of the quality survey */
	public DtSurveyID id;
	
	/** The survey result. */
	public PtString result;
	
	public DtDateAndTime instant;
	
	public PtBoolean init(DtSurveyID aId, PtString aResult, DtDateAndTime aInstant) {
		this.id = aId;
		this.result = aResult;
		this.instant = aInstant;
		
		return new PtBoolean(true);
	}
	

	public CtQualitySurvey() {
	}


	public PtBoolean isSentToAdministrator(ActAdministrator aActAdministrator, DtCoordinatorID aDtCoordinatorID) throws RemoteException {
		aActAdministrator.ieSendASurvey(aDtCoordinatorID, this);
		return new PtBoolean(true);
	}
	

	@Override
	public PtBoolean is() {
		return new PtBoolean(true);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CtQualitySurvey)) {
			return false;
		}
		
		CtQualitySurvey aObj = (CtQualitySurvey) obj;
		
		return id.equals(aObj.id)
				&& result.equals(aObj.result)
				&& instant.equals(aObj.instant); 
	}
	
	@Override
	public String toString() {
		return result.getValue();
	}
	
}
