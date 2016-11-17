package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

/**
 * The Class of the datatype Alert ID
 */
public class DtSurveyID extends DtString implements JIntIs {
	
	/** The Constant serialVersionUID */
	private static final long serialVersionUID = 227L;
	
	/**
	 * 
	 * @param s The string used to create the quality survey ID
	 */
	
	public DtSurveyID(PtString s) {
		super(s);
	}
 
	@Override
	public PtBoolean is() {
		return new PtBoolean(true);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DtSurveyID)) {
			return false;
		}
		return true;
	}
	
}
