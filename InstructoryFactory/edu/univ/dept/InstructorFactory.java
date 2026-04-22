/**
 * 
 */
package edu.univ.dept;

import java.util.List;

/**
 * 
 */
public interface InstructorFactory {
	Instructor fetchInstructorId(int id);
	List<Instructor> fetInstructors();
}
