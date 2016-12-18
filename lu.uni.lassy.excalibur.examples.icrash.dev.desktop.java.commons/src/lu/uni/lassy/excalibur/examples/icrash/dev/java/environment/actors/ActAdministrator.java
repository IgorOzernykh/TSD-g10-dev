/*******************************************************************************
 * Copyright (c) 2014-2015 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 *     Christophe Kamphaus - Remote implementation of Actors
 *     Thomas Mortimer - Updated client to MVC and added new design patterns
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtQualitySurvey;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

/**
 * The Interface ActAdministrator that allows RMI access to administrator functions.
 */
public interface ActAdministrator extends ActAuthenticated {

	/**
	 * Add a coordinator to the system, using the parameters passed.
	 *
	 * @param aDtCoordinatorID The ID to use when creating the coordinator
	 * @param aDtLogin The username to use when creating the coordinator
	 * @param aDtPassword The password to use when creating the coordinator
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	public PtBoolean oeAddCoordinator(DtCoordinatorID aDtCoordinatorID,
			DtLogin aDtLogin, DtPassword aDtPassword) throws RemoteException,
			NotBoundException;

	/**
	 * Delete a coordinator to the system, using the parameters passed.
	 *
	 * @param aDtCoordinatorID The ID to use when looking for the coordinator to delete
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	public PtBoolean oeDeleteCoordinator(DtCoordinatorID aDtCoordinatorID)
			throws RemoteException, NotBoundException;
	
	/**
	 * Returns the list of quality surveys submitted by the coordinator specified. 
	 * The list will be sent to the actor directly, via the ie method.
	 *  
	 * @param aDtCoordinatorID The ID to use when looking for the coordinator
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	public PtBoolean oeGetSurveys(DtCoordinatorID aDtCoordinatorID)
			throws RemoteException, NotBoundException;
	
	/**
	 * Returns the list of coordinators
	 * 
	 * @return The list of all coordinators in the system
	 * @throws RemoteException Thrown if the server is offline
	 */
	public List<CtCoordinator> getCoordinators() throws RemoteException, NotBoundException;
	
	/**
	 * A message and a quality survey is received by the user.
	 * 
	 * @param aCtCoordinator The coordinator associated with a survey
	 * @param aCtQualitySurvey The survey received by the user 
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieSendASurvey(DtCoordinatorID aDtCoordinatorID, CtQualitySurvey aCtQualitySurvey) throws RemoteException;
	
	/**
	 * A message sent to the listening actor saying the coordinator was created .
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieCoordinatorAdded() throws RemoteException;

	/**
	 * A message sent to the listening actor saying the coordinator was deleted.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieCoordinatorDeleted() throws RemoteException;
	
	/**
	 * A message sent to the listening actor saying the coordinator was updated.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieCoordinatorUpdated() throws RemoteException;	
}
