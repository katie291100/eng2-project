/**
 */
package microservice;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Controller</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link microservice.Controller#getName <em>Name</em>}</li>
 *   <li>{@link microservice.Controller#getEndpoints <em>Endpoints</em>}</li>
 * </ul>
 *
 * @see microservice.MicroservicePackage#getController()
 * @model
 * @generated
 */
public interface Controller extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see microservice.MicroservicePackage#getController_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link microservice.Controller#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Endpoints</b></em>' containment reference list.
	 * The list contents are of type {@link microservice.Endpoint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Endpoints</em>' containment reference list.
	 * @see microservice.MicroservicePackage#getController_Endpoints()
	 * @model containment="true"
	 * @generated
	 */
	EList<Endpoint> getEndpoints();

} // Controller
