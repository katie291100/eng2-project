/**
 */
package Y3877930;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see Y3877930.Y3877930Factory
 * @model kind="package"
 * @generated
 */
public interface Y3877930Package extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "Y3877930";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "Y3877930";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Y3877930Package eINSTANCE = Y3877930.impl.Y3877930PackageImpl.init();

	/**
	 * The meta object id for the '{@link Y3877930.impl.MicroserviceImpl <em>Microservice</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Y3877930.impl.MicroserviceImpl
	 * @see Y3877930.impl.Y3877930PackageImpl#getMicroservice()
	 * @generated
	 */
	int MICROSERVICE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROSERVICE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Entities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROSERVICE__ENTITIES = 1;

	/**
	 * The feature id for the '<em><b>Comsumers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROSERVICE__COMSUMERS = 2;

	/**
	 * The feature id for the '<em><b>Controllers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROSERVICE__CONTROLLERS = 3;

	/**
	 * The feature id for the '<em><b>Dtos</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROSERVICE__DTOS = 4;

	/**
	 * The feature id for the '<em><b>Repositories</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROSERVICE__REPOSITORIES = 5;

	/**
	 * The feature id for the '<em><b>Producers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROSERVICE__PRODUCERS = 6;

	/**
	 * The number of structural features of the '<em>Microservice</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROSERVICE_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Microservice</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MICROSERVICE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link Y3877930.impl.ControllerImpl <em>Controller</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Y3877930.impl.ControllerImpl
	 * @see Y3877930.impl.Y3877930PackageImpl#getController()
	 * @generated
	 */
	int CONTROLLER = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROLLER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Endpoints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROLLER__ENDPOINTS = 1;

	/**
	 * The number of structural features of the '<em>Controller</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROLLER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Controller</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROLLER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link Y3877930.impl.EndpointImpl <em>Endpoint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Y3877930.impl.EndpointImpl
	 * @see Y3877930.impl.Y3877930PackageImpl#getEndpoint()
	 * @generated
	 */
	int ENDPOINT = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT__ENTITY = 1;

	/**
	 * The number of structural features of the '<em>Endpoint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Endpoint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link Y3877930.impl.EntityImpl <em>Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Y3877930.impl.EntityImpl
	 * @see Y3877930.impl.Y3877930PackageImpl#getEntity()
	 * @generated
	 */
	int ENTITY = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__ATTRIBUTES = 1;

	/**
	 * The feature id for the '<em><b>Dto</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__DTO = 2;

	/**
	 * The number of structural features of the '<em>Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link Y3877930.impl.DTOImpl <em>DTO</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Y3877930.impl.DTOImpl
	 * @see Y3877930.impl.Y3877930PackageImpl#getDTO()
	 * @generated
	 */
	int DTO = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DTO__NAME = 0;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DTO__ATTRIBUTES = 1;

	/**
	 * The number of structural features of the '<em>DTO</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DTO_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>DTO</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DTO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link Y3877930.impl.RepositoryImpl <em>Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Y3877930.impl.RepositoryImpl
	 * @see Y3877930.impl.Y3877930PackageImpl#getRepository()
	 * @generated
	 */
	int REPOSITORY = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__ENTITY = 1;

	/**
	 * The number of structural features of the '<em>Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link Y3877930.impl.AttributeImpl <em>Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Y3877930.impl.AttributeImpl
	 * @see Y3877930.impl.Y3877930PackageImpl#getAttribute()
	 * @generated
	 */
	int ATTRIBUTE = 6;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__NAME = 1;

	/**
	 * The number of structural features of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link Y3877930.impl.ConsumerImpl <em>Consumer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Y3877930.impl.ConsumerImpl
	 * @see Y3877930.impl.Y3877930PackageImpl#getConsumer()
	 * @generated
	 */
	int CONSUMER = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSUMER__NAME = 0;

	/**
	 * The number of structural features of the '<em>Consumer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSUMER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Consumer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSUMER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link Y3877930.impl.ProducerImpl <em>Producer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Y3877930.impl.ProducerImpl
	 * @see Y3877930.impl.Y3877930PackageImpl#getProducer()
	 * @generated
	 */
	int PRODUCER = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCER__NAME = 0;

	/**
	 * The number of structural features of the '<em>Producer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Producer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link Y3877930.Type <em>Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Y3877930.Type
	 * @see Y3877930.impl.Y3877930PackageImpl#getType()
	 * @generated
	 */
	int TYPE = 9;

	/**
	 * The meta object id for the '{@link Y3877930.EndpointType <em>Endpoint Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Y3877930.EndpointType
	 * @see Y3877930.impl.Y3877930PackageImpl#getEndpointType()
	 * @generated
	 */
	int ENDPOINT_TYPE = 10;


	/**
	 * Returns the meta object for class '{@link Y3877930.Microservice <em>Microservice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Microservice</em>'.
	 * @see Y3877930.Microservice
	 * @generated
	 */
	EClass getMicroservice();

	/**
	 * Returns the meta object for the attribute '{@link Y3877930.Microservice#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see Y3877930.Microservice#getName()
	 * @see #getMicroservice()
	 * @generated
	 */
	EAttribute getMicroservice_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link Y3877930.Microservice#getEntities <em>Entities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entities</em>'.
	 * @see Y3877930.Microservice#getEntities()
	 * @see #getMicroservice()
	 * @generated
	 */
	EReference getMicroservice_Entities();

	/**
	 * Returns the meta object for the containment reference list '{@link Y3877930.Microservice#getComsumers <em>Comsumers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Comsumers</em>'.
	 * @see Y3877930.Microservice#getComsumers()
	 * @see #getMicroservice()
	 * @generated
	 */
	EReference getMicroservice_Comsumers();

	/**
	 * Returns the meta object for the containment reference list '{@link Y3877930.Microservice#getControllers <em>Controllers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Controllers</em>'.
	 * @see Y3877930.Microservice#getControllers()
	 * @see #getMicroservice()
	 * @generated
	 */
	EReference getMicroservice_Controllers();

	/**
	 * Returns the meta object for the containment reference list '{@link Y3877930.Microservice#getDtos <em>Dtos</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dtos</em>'.
	 * @see Y3877930.Microservice#getDtos()
	 * @see #getMicroservice()
	 * @generated
	 */
	EReference getMicroservice_Dtos();

	/**
	 * Returns the meta object for the containment reference list '{@link Y3877930.Microservice#getRepositories <em>Repositories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Repositories</em>'.
	 * @see Y3877930.Microservice#getRepositories()
	 * @see #getMicroservice()
	 * @generated
	 */
	EReference getMicroservice_Repositories();

	/**
	 * Returns the meta object for the containment reference list '{@link Y3877930.Microservice#getProducers <em>Producers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Producers</em>'.
	 * @see Y3877930.Microservice#getProducers()
	 * @see #getMicroservice()
	 * @generated
	 */
	EReference getMicroservice_Producers();

	/**
	 * Returns the meta object for class '{@link Y3877930.Controller <em>Controller</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Controller</em>'.
	 * @see Y3877930.Controller
	 * @generated
	 */
	EClass getController();

	/**
	 * Returns the meta object for the attribute '{@link Y3877930.Controller#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see Y3877930.Controller#getName()
	 * @see #getController()
	 * @generated
	 */
	EAttribute getController_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link Y3877930.Controller#getEndpoints <em>Endpoints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Endpoints</em>'.
	 * @see Y3877930.Controller#getEndpoints()
	 * @see #getController()
	 * @generated
	 */
	EReference getController_Endpoints();

	/**
	 * Returns the meta object for class '{@link Y3877930.Endpoint <em>Endpoint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Endpoint</em>'.
	 * @see Y3877930.Endpoint
	 * @generated
	 */
	EClass getEndpoint();

	/**
	 * Returns the meta object for the attribute '{@link Y3877930.Endpoint#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see Y3877930.Endpoint#getType()
	 * @see #getEndpoint()
	 * @generated
	 */
	EAttribute getEndpoint_Type();

	/**
	 * Returns the meta object for the reference '{@link Y3877930.Endpoint#getEntity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Entity</em>'.
	 * @see Y3877930.Endpoint#getEntity()
	 * @see #getEndpoint()
	 * @generated
	 */
	EReference getEndpoint_Entity();

	/**
	 * Returns the meta object for class '{@link Y3877930.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity</em>'.
	 * @see Y3877930.Entity
	 * @generated
	 */
	EClass getEntity();

	/**
	 * Returns the meta object for the attribute '{@link Y3877930.Entity#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see Y3877930.Entity#getName()
	 * @see #getEntity()
	 * @generated
	 */
	EAttribute getEntity_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link Y3877930.Entity#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see Y3877930.Entity#getAttributes()
	 * @see #getEntity()
	 * @generated
	 */
	EReference getEntity_Attributes();

	/**
	 * Returns the meta object for the reference '{@link Y3877930.Entity#getDto <em>Dto</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Dto</em>'.
	 * @see Y3877930.Entity#getDto()
	 * @see #getEntity()
	 * @generated
	 */
	EReference getEntity_Dto();

	/**
	 * Returns the meta object for class '{@link Y3877930.DTO <em>DTO</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DTO</em>'.
	 * @see Y3877930.DTO
	 * @generated
	 */
	EClass getDTO();

	/**
	 * Returns the meta object for the attribute '{@link Y3877930.DTO#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see Y3877930.DTO#getName()
	 * @see #getDTO()
	 * @generated
	 */
	EAttribute getDTO_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link Y3877930.DTO#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see Y3877930.DTO#getAttributes()
	 * @see #getDTO()
	 * @generated
	 */
	EReference getDTO_Attributes();

	/**
	 * Returns the meta object for class '{@link Y3877930.Repository <em>Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Repository</em>'.
	 * @see Y3877930.Repository
	 * @generated
	 */
	EClass getRepository();

	/**
	 * Returns the meta object for the attribute '{@link Y3877930.Repository#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see Y3877930.Repository#getName()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_Name();

	/**
	 * Returns the meta object for the reference '{@link Y3877930.Repository#getEntity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Entity</em>'.
	 * @see Y3877930.Repository#getEntity()
	 * @see #getRepository()
	 * @generated
	 */
	EReference getRepository_Entity();

	/**
	 * Returns the meta object for class '{@link Y3877930.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute</em>'.
	 * @see Y3877930.Attribute
	 * @generated
	 */
	EClass getAttribute();

	/**
	 * Returns the meta object for the attribute '{@link Y3877930.Attribute#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see Y3877930.Attribute#getType()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Type();

	/**
	 * Returns the meta object for the attribute '{@link Y3877930.Attribute#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see Y3877930.Attribute#getName()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Name();

	/**
	 * Returns the meta object for class '{@link Y3877930.Consumer <em>Consumer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Consumer</em>'.
	 * @see Y3877930.Consumer
	 * @generated
	 */
	EClass getConsumer();

	/**
	 * Returns the meta object for the attribute '{@link Y3877930.Consumer#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see Y3877930.Consumer#getName()
	 * @see #getConsumer()
	 * @generated
	 */
	EAttribute getConsumer_Name();

	/**
	 * Returns the meta object for class '{@link Y3877930.Producer <em>Producer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Producer</em>'.
	 * @see Y3877930.Producer
	 * @generated
	 */
	EClass getProducer();

	/**
	 * Returns the meta object for the attribute '{@link Y3877930.Producer#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see Y3877930.Producer#getName()
	 * @see #getProducer()
	 * @generated
	 */
	EAttribute getProducer_Name();

	/**
	 * Returns the meta object for enum '{@link Y3877930.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Type</em>'.
	 * @see Y3877930.Type
	 * @generated
	 */
	EEnum getType();

	/**
	 * Returns the meta object for enum '{@link Y3877930.EndpointType <em>Endpoint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Endpoint Type</em>'.
	 * @see Y3877930.EndpointType
	 * @generated
	 */
	EEnum getEndpointType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	Y3877930Factory getY3877930Factory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link Y3877930.impl.MicroserviceImpl <em>Microservice</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Y3877930.impl.MicroserviceImpl
		 * @see Y3877930.impl.Y3877930PackageImpl#getMicroservice()
		 * @generated
		 */
		EClass MICROSERVICE = eINSTANCE.getMicroservice();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MICROSERVICE__NAME = eINSTANCE.getMicroservice_Name();

		/**
		 * The meta object literal for the '<em><b>Entities</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MICROSERVICE__ENTITIES = eINSTANCE.getMicroservice_Entities();

		/**
		 * The meta object literal for the '<em><b>Comsumers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MICROSERVICE__COMSUMERS = eINSTANCE.getMicroservice_Comsumers();

		/**
		 * The meta object literal for the '<em><b>Controllers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MICROSERVICE__CONTROLLERS = eINSTANCE.getMicroservice_Controllers();

		/**
		 * The meta object literal for the '<em><b>Dtos</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MICROSERVICE__DTOS = eINSTANCE.getMicroservice_Dtos();

		/**
		 * The meta object literal for the '<em><b>Repositories</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MICROSERVICE__REPOSITORIES = eINSTANCE.getMicroservice_Repositories();

		/**
		 * The meta object literal for the '<em><b>Producers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MICROSERVICE__PRODUCERS = eINSTANCE.getMicroservice_Producers();

		/**
		 * The meta object literal for the '{@link Y3877930.impl.ControllerImpl <em>Controller</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Y3877930.impl.ControllerImpl
		 * @see Y3877930.impl.Y3877930PackageImpl#getController()
		 * @generated
		 */
		EClass CONTROLLER = eINSTANCE.getController();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTROLLER__NAME = eINSTANCE.getController_Name();

		/**
		 * The meta object literal for the '<em><b>Endpoints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTROLLER__ENDPOINTS = eINSTANCE.getController_Endpoints();

		/**
		 * The meta object literal for the '{@link Y3877930.impl.EndpointImpl <em>Endpoint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Y3877930.impl.EndpointImpl
		 * @see Y3877930.impl.Y3877930PackageImpl#getEndpoint()
		 * @generated
		 */
		EClass ENDPOINT = eINSTANCE.getEndpoint();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENDPOINT__TYPE = eINSTANCE.getEndpoint_Type();

		/**
		 * The meta object literal for the '<em><b>Entity</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENDPOINT__ENTITY = eINSTANCE.getEndpoint_Entity();

		/**
		 * The meta object literal for the '{@link Y3877930.impl.EntityImpl <em>Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Y3877930.impl.EntityImpl
		 * @see Y3877930.impl.Y3877930PackageImpl#getEntity()
		 * @generated
		 */
		EClass ENTITY = eINSTANCE.getEntity();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY__NAME = eINSTANCE.getEntity_Name();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY__ATTRIBUTES = eINSTANCE.getEntity_Attributes();

		/**
		 * The meta object literal for the '<em><b>Dto</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY__DTO = eINSTANCE.getEntity_Dto();

		/**
		 * The meta object literal for the '{@link Y3877930.impl.DTOImpl <em>DTO</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Y3877930.impl.DTOImpl
		 * @see Y3877930.impl.Y3877930PackageImpl#getDTO()
		 * @generated
		 */
		EClass DTO = eINSTANCE.getDTO();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DTO__NAME = eINSTANCE.getDTO_Name();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DTO__ATTRIBUTES = eINSTANCE.getDTO_Attributes();

		/**
		 * The meta object literal for the '{@link Y3877930.impl.RepositoryImpl <em>Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Y3877930.impl.RepositoryImpl
		 * @see Y3877930.impl.Y3877930PackageImpl#getRepository()
		 * @generated
		 */
		EClass REPOSITORY = eINSTANCE.getRepository();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY__NAME = eINSTANCE.getRepository_Name();

		/**
		 * The meta object literal for the '<em><b>Entity</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY__ENTITY = eINSTANCE.getRepository_Entity();

		/**
		 * The meta object literal for the '{@link Y3877930.impl.AttributeImpl <em>Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Y3877930.impl.AttributeImpl
		 * @see Y3877930.impl.Y3877930PackageImpl#getAttribute()
		 * @generated
		 */
		EClass ATTRIBUTE = eINSTANCE.getAttribute();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__TYPE = eINSTANCE.getAttribute_Type();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__NAME = eINSTANCE.getAttribute_Name();

		/**
		 * The meta object literal for the '{@link Y3877930.impl.ConsumerImpl <em>Consumer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Y3877930.impl.ConsumerImpl
		 * @see Y3877930.impl.Y3877930PackageImpl#getConsumer()
		 * @generated
		 */
		EClass CONSUMER = eINSTANCE.getConsumer();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSUMER__NAME = eINSTANCE.getConsumer_Name();

		/**
		 * The meta object literal for the '{@link Y3877930.impl.ProducerImpl <em>Producer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Y3877930.impl.ProducerImpl
		 * @see Y3877930.impl.Y3877930PackageImpl#getProducer()
		 * @generated
		 */
		EClass PRODUCER = eINSTANCE.getProducer();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRODUCER__NAME = eINSTANCE.getProducer_Name();

		/**
		 * The meta object literal for the '{@link Y3877930.Type <em>Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Y3877930.Type
		 * @see Y3877930.impl.Y3877930PackageImpl#getType()
		 * @generated
		 */
		EEnum TYPE = eINSTANCE.getType();

		/**
		 * The meta object literal for the '{@link Y3877930.EndpointType <em>Endpoint Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Y3877930.EndpointType
		 * @see Y3877930.impl.Y3877930PackageImpl#getEndpointType()
		 * @generated
		 */
		EEnum ENDPOINT_TYPE = eINSTANCE.getEndpointType();

	}

} //Y3877930Package
