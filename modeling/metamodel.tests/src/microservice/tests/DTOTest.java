/**
 */
package microservice.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import microservice.DTO;
import microservice.MicroserviceFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>DTO</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class DTOTest extends TestCase {

	/**
	 * The fixture for this DTO test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DTO fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(DTOTest.class);
	}

	/**
	 * Constructs a new DTO test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DTOTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this DTO test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(DTO fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this DTO test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DTO getFixture() {
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
		setFixture(MicroserviceFactory.eINSTANCE.createDTO());
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

} //DTOTest
