/**
 * 
 */
package edu.univ.dept;

/**
 * 
 */
public class Instructor implements Comparable<Instructor>{
	
	private int id;
	private int courseId;
	private String firstName;
	private String lastName;
	
	public Instructor () {
		
	}

	@Override
	public int compareTo(Instructor o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the courserId
	 */
	public int getCourseId() {
		return courseId;
	}

	/**
	 * @param courserId the courserId to set
	 */
	public void setCourserId(int courserId) {
		this.courseId = courserId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean equals(Object o) {
		return false;
	}
	
	/**
	 * 
	 */
	@Override
	public int hashCode() {
		//TODO: this
		return 0;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return lastName + ", " + firstName + " [" + id + "]" + " teaches" + "(" + courseId + ")";
	}

}
