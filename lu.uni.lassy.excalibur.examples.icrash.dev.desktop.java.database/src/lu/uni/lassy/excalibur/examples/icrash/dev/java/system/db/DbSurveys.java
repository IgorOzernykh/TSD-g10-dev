package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtQualitySurvey;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtSurveyID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.ICrashUtils;

/**
 * The Class for updating or retrieving quality surveys from the database. 
 */
public class DbSurveys extends DbAbstract {
	
	/**
	 * Inserts a quality survey into the database, using the properties from the CtQualitySurvey provided
	 * @param aDtQualitSurvey
	 */
	static public void insertQualitySurvey(CtQualitySurvey aCtSurvey) {
		try {
			conn = DriverManager.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");
			
			try {
				Statement st = conn.createStatement();
				
				String id = aCtSurvey.id.value.getValue();
				String result = aCtSurvey.result.getValue();
				
				int year = aCtSurvey.instant.date.year.value.getValue();
				int month = aCtSurvey.instant.date.month.value.getValue();
				int day = aCtSurvey.instant.date.day.value.getValue();

				int hour = aCtSurvey.instant.time.hour.value.getValue();
				int min = aCtSurvey.instant.time.minute.value.getValue();
				int sec = aCtSurvey.instant.time.second.value.getValue();

				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Calendar calendar = new GregorianCalendar(year, month, day,
						hour, min, sec);
				String instant = sdf.format(calendar.getTime());
				
				log.debug("[DATABASE]-Insert quality survey");
				int val = st.executeUpdate("INSERT INTO " + dbName + ".surveys" + "(id,result,instant)" 
				+ "VALUES('" + id + "','" + result + "','" + instant + "')");
				
				log.debug(val + " row(s) affected");	
			} catch (Exception e) {
				log.error("SQL statement is not executed! " + e);
			}
			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}
	}
	
	/**
	 * Get a single quality survey from the database with the ID as the provided.
	 * @param qualitySurveyId The ID of the quality survey to get
	 * @return The data retrieved from the database, this could be empty
	 */
	static public CtQualitySurvey getQualitySurvey(String qualitySurveyId) {
		CtQualitySurvey aCtQualitySurvey = new CtQualitySurvey();
		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			try {
				String sql = "SELECT * FROM " + dbName + ".surveys WHERE id = " + qualitySurveyId;
				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);
				
				if (res.next()) {
					DtSurveyID aId = new DtSurveyID(new PtString(res.getString("id")));
					PtString aResult = new PtString(res.getString("result"));
					Timestamp instant = res.getTimestamp("instant");
					Calendar cal = Calendar.getInstance();
					cal.setTime(instant);
					
					int d = cal.get(Calendar.DATE);
					int m = cal.get(Calendar.MONTH);
					int y = cal.get(Calendar.YEAR);
					DtDate aDtDate = ICrashUtils.setDate(y, m, d);
					int h = cal.get(Calendar.HOUR_OF_DAY);
					int min = cal.get(Calendar.MINUTE);
					int sec = cal.get(Calendar.SECOND);
					DtTime aDtTime = ICrashUtils.setTime(h, min, sec);
					DtDateAndTime aInstant = new DtDateAndTime(aDtDate, aDtTime);
					
					aCtQualitySurvey.init(aId, aResult, aInstant);
					
				}
			} catch (SQLException e) {
				log.error("SQL statement is not executed! " + e);
			}
			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}
		return aCtQualitySurvey;
	}
	
	/**
	 * Gets all of the surveys from the database.
	 *
	 * @return A hashtable of the surveys using their ID as a key value
	 */
	static public Hashtable<String, CtQualitySurvey> getSystemSurveys() {

		Hashtable<String, CtQualitySurvey> cmpSystemCtSurvey = new Hashtable<String, CtQualitySurvey>();

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT * FROM " + dbName + ".surveys";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				CtQualitySurvey aCtSurvey;

				while (res.next()) {

					aCtSurvey = new CtQualitySurvey();
					// survey's id
					DtSurveyID aId = new DtSurveyID(new PtString(
							res.getString("id")));
					
					// survey's result
					PtString aResult = new PtString(res.getString("result"));
					
					// survey's instant
					Timestamp instant = res.getTimestamp("instant");
					Calendar cal = Calendar.getInstance();
					cal.setTime(instant);

					int d = cal.get(Calendar.DATE);
					int m = cal.get(Calendar.MONTH);
					int y = cal.get(Calendar.YEAR);
					DtDate aDtDate = ICrashUtils.setDate(y, m, d);
					int h = cal.get(Calendar.HOUR_OF_DAY);
					int min = cal.get(Calendar.MINUTE);
					int sec = cal.get(Calendar.SECOND);
					DtTime aDtTime = ICrashUtils.setTime(h, min, sec);
					DtDateAndTime aInstant = new DtDateAndTime(aDtDate, aDtTime);
					aCtSurvey.init(aId, aResult, aInstant);

					//add instance to the hash
					cmpSystemCtSurvey.put(aCtSurvey.id.value.getValue(),
							aCtSurvey);

				}

			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			logException(e);
		}

		return cmpSystemCtSurvey;

	}
	
	/**
	 * Binds a coordinator to a survey.
	 *
	 * @param aCtSurvey The survey to bind the coordinator to
	 * @param aCtCoordinator The coordinator to bind to the survey
	 */
	static public void bindSurveyCoordinator(CtQualitySurvey aCtSurvey,
			CtCoordinator aCtCoordinator) {
		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Update

			try {
				String sql = "UPDATE " + dbName
						+ ".surveys SET coordinator =? WHERE id = ?";
				String id = aCtSurvey.id.value.getValue();
				String coordinatorId = aCtCoordinator.id.value.getValue();

				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, coordinatorId);
				statement.setString(2, id);
				int rows = statement.executeUpdate();
				log.debug(rows + " row(s) affected");
			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}

			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}
	}
	
	/**
	 * Gets the current highest number used for a quality survey ID in the database.
	 *
	 * @return the highest number for a quality survey id
	 */
	static public int getMaxSurveyID() {

		int maxSurveyId = 0;

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT MAX(CAST(id AS UNSIGNED)) FROM " + dbName
						+ ".surveys";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				if (res.next()) {
					maxSurveyId = res.getInt(1);
				}

			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			logException(e);
		}

		return maxSurveyId;

	}
	
	/**
	 * Delete a survey from the database.
	 *
	 * @param aCtSurvey The survey to delete from a database, it will use the ID to delete from the database
	 */
	static public void deleteQualitySurvey(CtQualitySurvey aCtSurvey) {

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Delete

			try {
				String sql = "DELETE FROM " + dbName + ".surveys WHERE id = ?";
				String id = aCtSurvey.id.value.getValue();

				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, id);
				int rows = statement.executeUpdate();
				log.debug(rows + " row(s) deleted");
			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}

			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}
	}
	
	/**
	 * Updates a survey with the CtQualitySurvey information provided.
	 *
	 * @param aCtSurvey Uses the ID to update the rest of the survey with the details provided
	 */
	static public void updateQualitySurvey(CtQualitySurvey aCtSurvey) {

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");
			/********************/
			//Update

			try {
				log.debug("[DATABASE]-Update survey");
				String sql = "UPDATE "
						+ dbName
						+ ".surveys SET `result` = ?, `instant` = ?";
				String id = aCtSurvey.id.value.getValue();
				String result = aCtSurvey.result.getValue();
				
				int year = aCtSurvey.instant.date.year.value.getValue();
				int month = aCtSurvey.instant.date.month.value.getValue();
				int day = aCtSurvey.instant.date.day.value.getValue();

				int hour = aCtSurvey.instant.time.hour.value.getValue();
				int min = aCtSurvey.instant.time.minute.value.getValue();
				int sec = aCtSurvey.instant.time.second.value.getValue();

				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Calendar calendar = new GregorianCalendar(year, month, day,
						hour, min, sec);
				String instant = sdf.format(calendar.getTime());


				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, result);
				statement.setString(2, instant);
				statement.setString(3, id);
				int rows = statement.executeUpdate();
				log.debug(rows + " row(s) affected");
			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}

			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}
	}

}
