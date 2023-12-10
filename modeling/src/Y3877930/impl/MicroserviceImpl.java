/**
 */
package Y3877930.impl;

import Y3877930.Consumer;
import Y3877930.Controller;
import Y3877930.DTO;
import Y3877930.Entity;
import Y3877930.Microservice;
import Y3877930.Producer;
import Y3877930.Repository;
import Y3877930.Y3877930Package;

import java.util.Collection;

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
 *   <li>{@link Y3877930.impl.MicroserviceImpl#getName <em>Name</em>}</li>
 *   <li>{@link Y3877930.impl.MicroserviceImpl#getEntities <em>Entities</em>}</li>
 *   <li>{@link Y3877930.impl.MicroserviceImpl#getComsumers <em>Comsumers</em>}</li>
 *   <li>{@link Y3877930.impl.MicroserviceImpl#getControllers <em>Controllers</em>}</li>
 *   <li>{@link Y3877930.impl.MicroserviceImpl#getDtos <em>Dtos</em>}</li>
 *   <li>{@link Y3877930.impl.MicroserviceImpl#getRepositories <em>Repositories</em>}</li>
 *   <li>{@link Y3877930.impl.MicroserviceImpl#getProducers <em>Producers</em>}</li>
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
		return Y3877930Package.Literals.MICROSERVICE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, Y3877930Package.MICROSERVICE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Entity> getEntities() {
		if (entities == null) {
			entities = new EObjectContainmentEList<Entity>(Entity.class, this, Y3877930Package.MICROSERVICE__ENTITIES);
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
			comsumers = new EObjectContainmentEList<Consumer>(Consumer.class, this, Y3877930Package.MICROSERVICE__COMSUMERS);
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
			controllers = new EObjectContainmentEList<Controller>(Controller.class, this, Y3877930Package.MICROSERVICE__CONTROLLERS);
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
			dtos = new EObjectContainmentEList<DTO>(DTO.class, this, Y3877930Package.MICROSERVICE__DTOS);
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
			repositories = new EObjectContainmentEList<Repository>(Repository.class, this, Y3877930Package.MICROSERVICE__REPOSITORIES);
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
			producers = new EObjectContainmentEList<Producer>(Producer.class, this, Y3877930Package.MICROSERVICE__PRODUCERS);
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
			case Y3877930Package.MICROSERVICE__ENTITIES:
				return ((InternalEList<?>)getEntities()).basicRemove(otherEnd, msgs);
			case Y3877930Package.MICROSERVICE__COMSUMERS:
				return ((InternalEList<?>)getComsumers()).basicRemove(otherEnd, msgs);
			case Y3877930Package.MICROSERVICE__CONTROLLERS:
				return ((InternalEList<?>)getControllers()).basicRemove(otherEnd, msgs);
			case Y3877930Package.MICROSERVICE__DTOS:
				return ((InternalEList<?>)getDtos()).basicRemove(otherEnd, msgs);
			case Y3877930Package.MICROSERVICE__REPOSITORIES:
				return ((InternalEList<?>)getRepositories()).basicRemove(otherEnd, msgs);
			case Y3877930Package.MICROSERVICE__PRODUCERS:
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
			case Y3877930Package.MICROSERVICE__NAME:
				return getName();
			case Y3877930Package.MICROSERVICE__ENTITIES:
				return getEntities();
			case Y3877930Package.MICROSERVICE__COMSUMERS:
				return getComsumers();
			case Y3877930Package.MICROSERVICE__CONTROLLERS:
				return getControllers();
			case Y3877930Package.MICROSERVICE__DTOS:
				return getDtos();
			case Y3877930Package.MICROSERVICE__REPOSITORIES:
				return getRepositories();
			case Y3877930Package.MICROSERVICE__PRODUCERS:
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
			case Y3877930Package.MICROSERVICE__NAME:
				setName((String)newValue);
				return;
			case Y3877930Package.MICROSERVICE__ENTITIES:
				getEntities().clear();
				getEntities().addAll((Collection<? extends Entity>)newValue);
				return;
			case Y3877930Package.MICROSERVICE__COMSUMERS:
				getComsumers().clear();
				getComsumers().addAll((Collection<? extends Consumer>)newValue);
				return;
			case Y3877930Package.MICROSERVICE__CONTROLLERS:
				getControllers().clear();
				getControllers().addAll((Collection<? extends Controller>)newValue);
				return;
			case Y3877930Package.MICROSERVICE__DTOS:
				getDtos().clear();
				getDtos().addAll((Collection<? extends DTO>)newValue);
				return;
			case Y3877930Package.MICROSERVICE__REPOSITORIES:
				getRepositories().clear();
				getRepositories().addAll((Collection<? extends Repository>)newValue);
				return;
			case Y3877930Package.MICROSERVICE__PRODUCERS:
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
			case Y3877930Package.MICROSERVICE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case Y3877930Package.MICROSERVICE__ENTITIES:
				getEntities().clear();
				return;
			case Y3877930Package.MICROSERVICE__COMSUMERS:
				getComsumers().clear();
				return;
			case Y3877930Package.MICROSERVICE__CONTROLLERS:
				getControllers().clear();
				return;
			case Y3877930Package.MICROSERVICE__DTOS:
				getDtos().clear();
				return;
			case Y3877930Package.MICROSERVICE__REPOSITORIES:
				getRepositories().clear();
				return;
			case Y3877930Package.MICROSERVICE__PRODUCERS:
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
			case Y3877930Package.MICROSERVICE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case Y3877930Package.MICROSERVICE__ENTITIES:
				return entities != null && !entities.isEmpty();
			case Y3877930Package.MICROSERVICE__COMSUMERS:
				return comsumers != null && !comsumers.isEmpty();
			case Y3877930Package.MICROSERVICE__CONTROLLERS:
				return controllers != null && !controllers.isEmpty();
			case Y3877930Package.MICROSERVICE__DTOS:
				return dtos != null && !dtos.isEmpty();
			case Y3877930Package.MICROSERVICE__REPOSITORIES:
				return repositories != null && !repositories.isEmpty();
			case Y3877930Package.MICROSERVICE__PRODUCERS:
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
