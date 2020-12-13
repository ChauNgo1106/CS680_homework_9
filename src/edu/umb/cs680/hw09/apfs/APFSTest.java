package edu.umb.cs680.hw09.apfs;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import edu.umb.cs680.hw09.fat.FAT;

public class APFSTest {
	// setting up object
	private static ApfsDirectory root;
	private static ApfsDirectory home;
	private static ApfsDirectory applications;
	private static ApfsDirectory code;
	private static ApfsFile a;
	private static ApfsFile b;
	private static ApfsFile c;
	private static ApfsFile d;
	private static ApfsFile e;
	private static ApfsFile f;
	private static ApfsLink x;
	private static ApfsLink y;
	private static ApfsLink z;

	@BeforeAll
	public static void setUp() {
		// initialize APFS file system
		APFS.getFileSystem().initFileSystemAPFS("APFS", 10240);
		
		// assign root in APFS system into root variable
		root = APFS.getFileSystem().getRootDir();

		applications = new ApfsDirectory(root, "applications", 0, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 00, 40)),
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 00, 40)));

		home = new ApfsDirectory(root, "home", 0, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 10, 15), LocalTime.of(20, 30, 00)),
				LocalDateTime.of(LocalDate.of(2019, 10, 15), LocalTime.of(20, 30, 00)));
		code = new ApfsDirectory(home, "code", 0, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 31, 00)),
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 31, 00)));

		a = new ApfsFile(applications, "a", 2048, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 01, 00)),
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 01, 00)));
		b = new ApfsFile(applications, "b", 1024, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 10, 00)),
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 10, 00)));

		c = new ApfsFile(home, "c", 2048, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 10, 15), LocalTime.of(21, 32, 45)),
				LocalDateTime.of(LocalDate.of(2019, 10, 15), LocalTime.of(21, 32, 45)));
		d = new ApfsFile(home, "d", 4096, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(21, 45, 18)),
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(21, 45, 18)));

		e = new ApfsFile(code, "e", 1024, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 40, 59)),
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 40, 59)));
		f = new ApfsFile(code, "f", 2048, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 45, 51)),
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 45, 51)));
		// symbolic Link
		x = new ApfsLink(home, "x", 0, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 11, 17), LocalTime.of(20, 46, 00)),
				LocalDateTime.of(LocalDate.of(2019, 11, 17), LocalTime.of(20, 46, 00)), applications);
		y = new ApfsLink(code, "y", 0, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 9, 16), LocalTime.of(20, 39, 00)),
				LocalDateTime.of(LocalDate.of(2019, 9, 16), LocalTime.of(20, 39, 00)), b);

		applications.appendChild(a);
		applications.appendChild(b);

		root.appendChild(applications);
		root.appendChild(home);

		home.appendChild(code);
		home.appendChild(c);
		home.appendChild(d);
		home.appendChild(x);

		code.appendChild(e);
		code.appendChild(f);
		code.appendChild(y);
	}

	@Test
	public void checkInstanceOfDirectory() {
		assertTrue(root instanceof ApfsDirectory);
		assertTrue(code instanceof ApfsDirectory);
		assertTrue(home instanceof ApfsDirectory);
		assertTrue(applications instanceof ApfsDirectory);
	}

	@Test
	public void checkInstanceOfFSElement() {
		// Directories
		assertTrue(root instanceof ApfsElement);
		assertTrue(code instanceof ApfsElement);
		assertTrue(home instanceof ApfsElement);
		assertTrue(applications instanceof ApfsElement);

		// Files
		assertTrue(a instanceof ApfsElement);
		assertTrue(b instanceof ApfsElement);
		assertTrue(c instanceof ApfsElement);
		assertTrue(d instanceof ApfsElement);
		assertTrue(e instanceof ApfsElement);
		assertTrue(f instanceof ApfsElement);

	}

	@Test
	public void checkInstanceOfFile() {
		assertTrue(a instanceof ApfsFile);
		assertTrue(b instanceof ApfsFile);
		assertTrue(c instanceof ApfsFile);
		assertTrue(d instanceof ApfsFile);
		assertTrue(e instanceof ApfsFile);
		assertTrue(f instanceof ApfsFile);
	}

	// convert FSElement data fields into array String
	private String[] FSElementToString(ApfsElement element) {
		String[] result = { element.getParent().getName(), element.getName(), Integer.toString(element.getSize()),
				element.getOwner(), element.getCreationTime(), element.getLastModified() };
		return result;
	}

	// convert root String, root has null parent
	private String[] rootToString(ApfsDirectory element) {
		String[] result = { element.getName(), Integer.toString(element.getSize()), element.getOwner(),
				element.getCreationTime(), element.getLastModified() };
		return result;
	}

	// convert system info into array of String
	private String[] systemInfo(APFS element) {
		String[] result = { element.getName(), Integer.toString(element.getCapacity()),
				element.getRootDir().getName() };
		return result;
	}

	@Test
	public void verifygetterMethodsOfHomeDirectory() {
		String[] expected = { "root", "home", "0", "Chau Ngo", "2019-10-15T20:30", "2019-10-15T20:30" };
		assertArrayEquals(expected, FSElementToString(home));
	}

	@Test
	public void verifygetterMethodsOfRootDirectory() {
		String[] expected = { "root", "0", "Chau Ngo", "2019-11-15T20:40:59", "2019-11-15T20:40:59" };
		assertArrayEquals(expected, rootToString(root));
	}

	@Test
	public void verifygetterMethodsOfCodeDirectory() {
		String[] expected = { "home", "code", "0", "Chau Ngo", "2019-11-15T20:31", "2019-11-15T20:31" };
		assertArrayEquals(expected, FSElementToString(code));
	}

	@Test
	public void verify_File_e_and_f_are_a_children_of_code_directory() {
		assertEquals(f.getParent(), code);
		assertEquals(e.getParent(), code);
	}

	@Test
	public void verify_File_a_and_b_are_a_children_of_applications_directory() {
		assertEquals(a.getParent(), applications);
		assertEquals(b.getParent(), applications);
	}

	@Test
	public void verify_File_c_and_d_are_a_children_of_home_directory() {
		assertEquals(c.getParent(), home);
		assertEquals(d.getParent(), home);
	}

	@Test
	public void verify_directory_home_and_applications_are_a_children_of_root_directory() {
		assertEquals(home.getParent(), root);
		assertEquals(applications.getParent(), root);

	}

	// checking the singleton of class APFS
	@Test
	public void checking_the_singleton_of_APFS_class() {
		APFS a = APFS.getFileSystem();
		assertEquals(a.hashCode(), APFS.getFileSystem().hashCode());
	}

	//checking the children on each ApfsDirectory
	@Test
	public void verify_children_of_applications_directory() {
		int num_of_children_expected = 2;
		assertEquals(num_of_children_expected, applications.countChildren());

		assertEquals(applications.getChildren().get(0).hashCode(), a.hashCode());
		assertEquals(applications.getChildren().get(1).hashCode(), b.hashCode());

	}

	@Test
	public void verify_children_of_home_directory() {
		int num_of_children_expected = 4;
		assertEquals(num_of_children_expected, home.countChildren());

		assertEquals(home.getChildren().get(0).hashCode(), code.hashCode());
		assertEquals(home.getChildren().get(1).hashCode(), c.hashCode());
		assertEquals(home.getChildren().get(2).hashCode(), d.hashCode());

	}

	@Test
	public void verify_children_of_code_directory() {
		int num_of_children_expected = 3;
		assertEquals(num_of_children_expected, code.countChildren());

		assertEquals(code.getChildren().get(0).hashCode(), e.hashCode());
		assertEquals(code.getChildren().get(1).hashCode(), f.hashCode());
	}
	//getFiles of code directory
	@Test
	public void verify_files_of_code_directory() {
		// expected: code has two ApfsFiles e and f
		int numFiles = 3;
		assertEquals(numFiles, code.getFiles().size());
		assertEquals(code.getFiles().get(0).hashCode(), e.hashCode());
		assertEquals(code.getFiles().get(1).hashCode(), f.hashCode());
		assertEquals(code.getFiles().get(2).hashCode(), y.hashCode());
	}

	@Test
	public void verify_files_of_home_directory() {

		// expected: code has two ApfsFiles e and f
		int numFiles = 3;
		assertEquals(numFiles, home.getFiles().size());
		assertEquals(home.getFiles().get(0).hashCode(), c.hashCode());
		assertEquals(home.getFiles().get(1).hashCode(), d.hashCode());
		assertEquals(home.getFiles().get(2).hashCode(), x.hashCode());
	}
	//getSubDirectories of code directory
	@Test
	public void verify_subdirectories_of_home_directory() {
		// expected: home has only one subdirectory that is code
		int numSubDirectories = 1;
		assertEquals(numSubDirectories, home.getSubDirectories().size());
		assertEquals(home.getSubDirectories().get(0).hashCode(), code.hashCode());
	}


	@Test
	public void checking_total_size_of_root() {
		int root_total_size = a.getSize() + b.getSize() + c.getSize() + d.getSize() + e.getSize() + f.getSize()
				+ x.getSize() + y.getSize();
		assertEquals(root_total_size, root.getTotalSize());
	}

	@Test
	public void checking_total_size_of_applications() {
		int applications_total_size = a.getSize() + b.getSize();
		assertEquals(applications_total_size, applications.getTotalSize());
	}

	@Test
	public void checking_total_size_of_code() {
		int code_total_size = e.getSize() + f.getSize() + y.getSize();
		assertEquals(code_total_size, code.getTotalSize());
	}

	@Test
	public void checking_total_size_of_home() {
		int home_total_size = e.getSize() + f.getSize() + c.getSize() + d.getSize() + x.getSize() + y.getSize();
		assertEquals(home_total_size, home.getTotalSize());
	};

	@Test
	public void checking_the_target_of_link_y() {

		String[] expected = { "applications", "b", "1024", "Chau Ngo", "2019-09-15T13:10", "2019-09-15T13:10", };
		assertEquals(y.getTarget().getName(), b.getName());
		assertArrayEquals(expected, FSElementToString(b));
	}

	@Test
	public void checking_the_target_of_link_x() {
		String[] expected = { "root", "applications", "0", "Chau Ngo", "2019-09-15T13:00:40", "2019-09-15T13:00:40" };
		assertEquals(x.getTarget().getName(), applications.getName());
		assertArrayEquals(expected, FSElementToString(applications));
	}

	// checking the init system information
	@Test
	public void checking_the_init_APFS() {
		String[] expected = { "APFS", "10240", "root" };
		assertArrayEquals(expected, systemInfo(APFS.getFileSystem()));
	}

	// throw an error when name exceeds 255 characters
	@Test
	public void checking_limit_characters_of_a_file_Name() {
		try {
		ApfsFile file1 = new ApfsFile(applications, "aasdsadasdadaasdasdasdasdasdasdadadadadadadasdasdadaljndjsn cjsan cjnsacjnasjcnjncajcnasjcnaljscaljc as casncjsacnajsncsjncajsncajlcaslcnsacaslncnasjlcnalscjnalscnascnalsjcnlasdasdasdsadsadsadsadsadasdaadasjnclajscnascnlsajcnlsjacnlsajclsajcnalclasjcnlsajcncnacaljscn"
				, 2048, "Chau Ngo", LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 01, 00)), LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 01, 00)));
		}catch(IllegalArgumentException ex) {
			assertEquals("File name is allowed up to 255 characters" , ex.getMessage() );
		}
	}

}
