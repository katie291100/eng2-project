/**
 */
package Y3877930;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Microservice</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link Y3877930.Microservice#getName <em>Name</em>}</li>
 *   <li>{@link Y3877930.Microservice#getEntities <em>Entities</em>}</li>
 *   <li>{@link Y3877930.Microservice#getComsumers <em>Comsumers</em>}</li>
 *   <li>{@link Y3877930.Microservice#getControllers <em>Controllers</em>}</li>
 *   <li>{@link Y3877930.Microservice#getDtos <em>Dtos</em>}</li>
 *   <li>{@link Y3877930.Microservice#getRepositories <em>Repositories</em>}</li>
 *   <li>{@link Y3877930.Microservice#getProducers <em>Producers</em>}</li>
 * </ul>
 *
 * @see Y3877930.Y3877930Package#getMicroservice()
 * @model
 * @generated
 */
public interface Microservice extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see Y3877930.Y3877930Package#getMicroservice_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link Y3877930.Microservice#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Entities</b></em>' containment reference list.
	 * The list contents are of type {@link Y3877930.Entity}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entities</em>' containment reference list.
	 * @see Y3877930.Y3877930Package#getMicroservice_Entities()
	 * @model containment="true"
	 * @generated
	 */
	EList<Entity> getEntities();

	/**
	 * Returns the value of the '<em><b>Comsumers</b></em>' containment reference list.
	 * The list contents are of type {@link Y3877930.Consumer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comsumers</em>' containment reference list.
	 * @see Y3877930.Y3877930Package#getMicroservice_Comsumers()
	 * @model containment="true"
	 * @generated
	 */
	EList<Consumer> getComsumers();

	/**
	 * Returns the value of the '<em><b>Controllers</b></em>' containment reference list.
	 * The list contents are of type {@link Y3877930.Controller}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Controllers</em>' containment reference list.
	 * @see Y3877930.Y3877930Package#getMicroservice_Controllers()
	 * @model containment="true"
	 * @generated
	 */
	EList<Controller> getControllers();

	/**
	 * Returns the value of the '<em><b>Dtos</b></em>' containment reference list.
	 * The list contents are of type {@link Y3877930.DTO}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dtos</em>' containment reference list.
	 * @see Y3877930.Y3877930Package#getMicroservice_Dtos()
	 * @model containment="true"
	 * @generated
	 */
	EList<DTO> getDtos();

	/**
	 * Returns the value of the '<em><b>Repositories</b></em>' containment reference list.
	 * The list contents are of type {@link Y3877930.Repository}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repositories</em>' containment reference list.
	 * @see Y3877930.Y3877930Package#getMicroservice_Repositories()
	 * @model containment="true"
	 * @generated
	 */
	EList<Repository> getRepositories();

	/**
	 * Returns the value of the '<em><b>Producers</b></em>' containment reference list.
	 * The list contents are of type {@link Y3877930.Producer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Producers</em>' containment reference list.
	 * @see Y3877930.Y3877930Package#getMicroservice_Producers()
	 * @model containment="true"
	 * @generated
	 */
	EList<Producer> getProducers();

} // Microservice
