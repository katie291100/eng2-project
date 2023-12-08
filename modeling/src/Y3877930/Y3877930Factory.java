/**
 */
package Y3877930;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see Y3877930.Y3877930Package
 * @generated
 */
public interface Y3877930Factory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Y3877930Factory eINSTANCE = Y3877930.impl.Y3877930FactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Microservice</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Microservice</em>'.
	 * @generated
	 */
	Microservice createMicroservice();

	/**
	 * Returns a new object of class '<em>Controller</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Controller</em>'.
	 * @generated
	 */
	Controller createController();

	/**
	 * Returns a new object of class '<em>Endpoint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Endpoint</em>'.
	 * @generated
	 */
	Endpoint createEndpoint();

	/**
	 * Returns a new object of class '<em>Entity</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Entity</em>'.
	 * @generated
	 */
	Entity createEntity();

	/**
	 * Returns a new object of class '<em>DTO</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DTO</em>'.
	 * @generated
	 */
	DTO createDTO();

	/**
	 * Returns a new object of class '<em>Repository</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Repository</em>'.
	 * @generated
	 */
	Repository createRepository();

	/**
	 * Returns a new object of class '<em>Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute</em>'.
	 * @generated
	 */
	Attribute createAttribute();

	/**
	 * Returns a new object of class '<em>Consumer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Consumer</em>'.
	 * @generated
	 */
	Consumer createConsumer();

	/**
	 * Returns a new object of class '<em>Producer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Producer</em>'.
	 * @generated
	 */
	Producer createProducer();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	Y3877930Package getY3877930Package();

} //Y3877930Factory
