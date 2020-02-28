package de.nullnull.product;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ProductRepositoryWalkerTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Before
	public void setUp() throws Exception {
		File repoDir = tempFolder.newFolder("repo");
		File productDir = new File(repoDir, "fantasy");
		if (!productDir.mkdir())
			throw new IOException("Problem beim Erstellen von '" + productDir + "'");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testList() throws MalformedURLException, IOException {
		ProductRepositoryWalker repositoryWalker = new DefaultProductRepositoryWalker(
				new DefaultProductRepository("default", tempFolder.newFolder().toURI().toURL().toString()));
		List<ProductPackage> list = repositoryWalker.list();
	}

}
