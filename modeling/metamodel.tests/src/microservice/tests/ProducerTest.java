/**
 */
package microservice.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import microservice.MicroserviceFactory;
import microservice.Producer;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Producer</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProducerTest extends TestCase {

	/**
	 * The fixture for this Producer test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Producer fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ProducerTest.class);
	}

	/**
	 * Constructs a new Producer test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProducerTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Producer test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Producer fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Producer test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Producer getFixture() {
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
		setFixture(MicroserviceFactory.eINSTANCE.createProducer());
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

} //ProducerTest
