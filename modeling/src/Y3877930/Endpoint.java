/**
 */
package Y3877930;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Endpoint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link Y3877930.Endpoint#getType <em>Type</em>}</li>
 *   <li>{@link Y3877930.Endpoint#getEntity <em>Entity</em>}</li>
 * </ul>
 *
 * @see Y3877930.Y3877930Package#getEndpoint()
 * @model
 * @generated
 */
public interface Endpoint extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link Y3877930.EndpointType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see Y3877930.EndpointType
	 * @see #setType(EndpointType)
	 * @see Y3877930.Y3877930Package#getEndpoint_Type()
	 * @model
	 * @generated
	 */
	EndpointType getType();

	/**
	 * Sets the value of the '{@link Y3877930.Endpoint#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see Y3877930.EndpointType
	 * @see #getType()
	 * @generated
	 */
	void setType(EndpointType value);

	/**
	 * Returns the value of the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity</em>' reference.
	 * @see #setEntity(Entity)
	 * @see Y3877930.Y3877930Package#getEndpoint_Entity()
	 * @model
	 * @generated
	 */
	Entity getEntity();

	/**
	 * Sets the value of the '{@link Y3877930.Endpoint#getEntity <em>Entity</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity</em>' reference.
	 * @see #getEntity()
	 * @generated
	 */
	void setEntity(Entity value);

} // Endpoint
