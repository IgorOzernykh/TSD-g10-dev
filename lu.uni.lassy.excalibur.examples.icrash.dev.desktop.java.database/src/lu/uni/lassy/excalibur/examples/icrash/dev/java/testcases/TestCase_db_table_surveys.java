package lu.uni.lassy.excalibur.examples.icrash.dev.java.testcases;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db.DbSurveys;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtQualitySurvey;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtSurveyID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.ICrashUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;

/**
 * The Class for testing the database table of surveys.
 */
public class TestCase_db_table_surveys {
	/** The logger used to log issues or information. */
	static Logger log = Log4JUtils.getInstance().getLogger();

	
	/**
	 * The main method, which runs the test.
	 *
	 * @param args the arguments passed to the system, these are ignored
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server is not bound correctly in RMI settings
	 */
	public static void main(String[] args) throws RemoteException, NotBoundException {

		log.info("---- test insert-------");
		//**********************************************************
		//set up id
		DtSurveyID aId = new DtSurveyID(new PtString("1"));
		
		//**********************************************************
		//set up result
		PtString aResult = new PtString("1/5 2/5 4/5 1/5 2/6 3/5 ");
		
		//**********************************************************
		//set up instant
		int d,m,y,h,min,sec;
		d=26; m=11;	 y=2017;
		DtDate aDtDate = ICrashUtils.setDate(y, m, d);
		h=10; min=10; sec=16;
		DtTime aDtTime = ICrashUtils.setTime(h, min, sec);
		DtDateAndTime aInstant = new DtDateAndTime(aDtDate,aDtTime);
		
		CtQualitySurvey aCtSurvey = new CtQualitySurvey();
		aCtSurvey.init(aId, aResult, aInstant);
		
		DbSurveys.insertQualitySurvey(aCtSurvey);
		

		log.info("---- test select -------");		
		CtQualitySurvey aCtSurvey2 = DbSurveys.getQualitySurvey(aId.value.getValue());
		log.debug("The retrieved survey's id is " + aCtSurvey2.id.value.getValue());
		log.debug("The retrieved survey's result is " + aCtSurvey2.result.getValue());
		log.debug("The retrieved survey's timestamp is " + aCtSurvey2.instant.toString());
		
		DbSurveys.deleteQualitySurvey(aCtSurvey2);
		
	}

}
