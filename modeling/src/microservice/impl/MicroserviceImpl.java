/**
 */
package microservice.impl;

import java.util.Collection;

import microservice.Consumer;
import microservice.Controller;
import microservice.DTO;
import microservice.Entity;
import microservice.Microservice;
import microservice.MicroservicePackage;
import microservice.Producer;
import microservice.Repository;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Microservice</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link microservice.impl.MicroserviceImpl#getName <em>Name</em>}</li>
 *   <li>{@link microservice.impl.MicroserviceImpl#getEntities <em>Entities</em>}</li>
 *   <li>{@link microservice.impl.MicroserviceImpl#getComsumers <em>Comsumers</em>}</li>
 *   <li>{@link microservice.impl.MicroserviceImpl#getControllers <em>Controllers</em>}</li>
 *   <li>{@link microservice.impl.MicroserviceImpl#getDtos <em>Dtos</em>}</li>
 *   <li>{@link microservice.impl.MicroserviceImpl#getRepositories <em>Repositories</em>}</li>
 *   <li>{@link microservice.impl.MicroserviceImpl#getProducers <em>Producers</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MicroserviceImpl extends MinimalEObjectImpl.Container implements Microservice {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEntities() <em>Entities</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntities()
	 * @generated
	 * @ordered
	 */
	protected EList<Entity> entities;

	/**
	 * The cached value of the '{@link #getComsumers() <em>Comsumers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComsumers()
	 * @generated
	 * @ordered
	 */
	protected EList<Consumer> comsumers;

	/**
	 * The cached value of the '{@link #getControllers() <em>Controllers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getControllers()
	 * @generated
	 * @ordered
	 */
	protected EList<Controller> controllers;

	/**
	 * The cached value of the '{@link #getDtos() <em>Dtos</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDtos()
	 * @generated
	 * @ordered
	 */
	protected EList<DTO> dtos;

	/**
	 * The cached value of the '{@link #getRepositories() <em>Repositories</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepositories()
	 * @generated
	 * @ordered
	 */
	protected EList<Repository> repositories;

	/**
	 * The cached value of the '{@link #getProducers() <em>Producers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProducers()
	 * @generated
	 * @ordered
	 */
	protected EList<Producer> producers;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MicroserviceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MicroservicePackage.Literals.MICROSERVICE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MicroservicePackage.MICROSERVICE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Entity> getEntities() {
		if (entities == null) {
			entities = new EObjectContainmentEList<Entity>(Entity.class, this, MicroservicePackage.MICROSERVICE__ENTITIES);
		}
		return entities;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Consumer> getComsumers() {
		if (comsumers == null) {
			comsumers = new EObjectContainmentEList<Consumer>(Consumer.class, this, MicroservicePackage.MICROSERVICE__COMSUMERS);
		}
		return comsumers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Controller> getControllers() {
		if (controllers == null) {
			controllers = new EObjectContainmentEList<Controller>(Controller.class, this, MicroservicePackage.MICROSERVICE__CONTROLLERS);
		}
		return controllers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DTO> getDtos() {
		if (dtos == null) {
			dtos = new EObjectContainmentEList<DTO>(DTO.class, this, MicroservicePackage.MICROSERVICE__DTOS);
		}
		return dtos;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Repository> getRepositories() {
		if (repositories == null) {
			repositories = new EObjectContainmentEList<Repository>(Repository.class, this, MicroservicePackage.MICROSERVICE__REPOSITORIES);
		}
		return repositories;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Producer> getProducers() {
		if (producers == null) {
			producers = new EObjectContainmentEList<Producer>(Producer.class, this, MicroservicePackage.MICROSERVICE__PRODUCERS);
		}
		return producers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MicroservicePackage.MICROSERVICE__ENTITIES:
				return ((InternalEList<?>)getEntities()).basicRemove(otherEnd, msgs);
			case MicroservicePackage.MICROSERVICE__COMSUMERS:
				return ((InternalEList<?>)getComsumers()).basicRemove(otherEnd, msgs);
			case MicroservicePackage.MICROSERVICE__CONTROLLERS:
				return ((InternalEList<?>)getControllers()).basicRemove(otherEnd, msgs);
			case MicroservicePackage.MICROSERVICE__DTOS:
				return ((InternalEList<?>)getDtos()).basicRemove(otherEnd, msgs);
			case MicroservicePackage.MICROSERVICE__REPOSITORIES:
				return ((InternalEList<?>)getRepositories()).basicRemove(otherEnd, msgs);
			case MicroservicePackage.MICROSERVICE__PRODUCERS:
				return ((InternalEList<?>)getProducers()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MicroservicePackage.MICROSERVICE__NAME:
				return getName();
			case MicroservicePackage.MICROSERVICE__ENTITIES:
				return getEntities();
			case MicroservicePackage.MICROSERVICE__COMSUMERS:
				return getComsumers();
			case MicroservicePackage.MICROSERVICE__CONTROLLERS:
				return getControllers();
			case MicroservicePackage.MICROSERVICE__DTOS:
				return getDtos();
			case MicroservicePackage.MICROSERVICE__REPOSITORIES:
				return getRepositories();
			case MicroservicePackage.MICROSERVICE__PRODUCERS:
				return getProducers();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MicroservicePackage.MICROSERVICE__NAME:
				setName((String)newValue);
				return;
			case MicroservicePackage.MICROSERVICE__ENTITIES:
				getEntities().clear();
				getEntities().addAll((Collection<? extends Entity>)newValue);
				return;
			case MicroservicePackage.MICROSERVICE__COMSUMERS:
				getComsumers().clear();
				getComsumers().addAll((Collection<? extends Consumer>)newValue);
				return;
			case MicroservicePackage.MICROSERVICE__CONTROLLERS:
				getControllers().clear();
				getControllers().addAll((Collection<? extends Controller>)newValue);
				return;
			case MicroservicePackage.MICROSERVICE__DTOS:
				getDtos().clear();
				getDtos().addAll((Collection<? extends DTO>)newValue);
				return;
			case MicroservicePackage.MICROSERVICE__REPOSITORIES:
				getRepositories().clear();
				getRepositories().addAll((Collection<? extends Repository>)newValue);
				return;
			case MicroservicePackage.MICROSERVICE__PRODUCERS:
				getProducers().clear();
				getProducers().addAll((Collection<? extends Producer>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MicroservicePackage.MICROSERVICE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case MicroservicePackage.MICROSERVICE__ENTITIES:
				getEntities().clear();
				return;
			case MicroservicePackage.MICROSERVICE__COMSUMERS:
				getComsumers().clear();
				return;
			case MicroservicePackage.MICROSERVICE__CONTROLLERS:
				getControllers().clear();
				return;
			case MicroservicePackage.MICROSERVICE__DTOS:
				getDtos().clear();
				return;
			case MicroservicePackage.MICROSERVICE__REPOSITORIES:
				getRepositories().clear();
				return;
			case MicroservicePackage.MICROSERVICE__PRODUCERS:
				getProducers().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MicroservicePackage.MICROSERVICE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case MicroservicePackage.MICROSERVICE__ENTITIES:
				return entities != null && !entities.isEmpty();
			case MicroservicePackage.MICROSERVICE__COMSUMERS:
				return comsumers != null && !comsumers.isEmpty();
			case MicroservicePackage.MICROSERVICE__CONTROLLERS:
				return controllers != null && !controllers.isEmpty();
			case MicroservicePackage.MICROSERVICE__DTOS:
				return dtos != null && !dtos.isEmpty();
			case MicroservicePackage.MICROSERVICE__REPOSITORIES:
				return repositories != null && !repositories.isEmpty();
			case MicroservicePackage.MICROSERVICE__PRODUCERS:
				return producers != null && !producers.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //MicroserviceImpl
