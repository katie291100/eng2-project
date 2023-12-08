/**
 */
package microservice.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import microservice.Endpoint;
import microservice.MicroserviceFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Endpoint</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class EndpointTest extends TestCase {

	/**
	 * The fixture for this Endpoint test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Endpoint fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(EndpointTest.class);
	}

	/**
	 * Constructs a new Endpoint test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndpointTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Endpoint test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Endpoint fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Endpoint test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Endpoint getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(MicroserviceFactory.eINSTANCE.createEndpoint());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //EndpointTest
