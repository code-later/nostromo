/*
 * MediaContainerTest.java
 *
 * Version 1.0  Nov 17, 2008
 *
 * Copyright notice
 *
 * Brief description
 *
 * (c) 2008 by dbreuer
 */
package de.fhkoeln.cosima.media;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.fhkoeln.cosima.media.AbstractMedia;
import de.fhkoeln.cosima.media.MediaContainer;
import de.fhkoeln.cosima.media.MediaData;
import de.fhkoeln.cosima.media.Metadata;



/**
 * Documentation comment without implementation details. 
 * Use implementation comments to describe details of the implementation.
 * Comment lines should not be longer than 70 characters.
 *
 * @author dbreuer
 * @version 1.0  Nov 17, 2008
 *
 */
public class MediaContainerTest {
  
  private AbstractMedia data;
  private AbstractMedia subContainer;
  private List<Metadata> metadataList;
  
  @Before
  public void setup() {
    this.data = mock(MediaData.class);
    this.subContainer = mock(MediaContainer.class);
    this.metadataList = mock(List.class);

    when(data.getName()).thenReturn("LeafMedia");
    when(subContainer.getName()).thenReturn("ContainerMedia");
  }
  
  @Test
  public void testShouldConsistsOfAbstractMediaObjects() {
    MediaContainer container = new MediaContainer(metadataList);

    assertTrue(container.size() == 0);
    container.addMedia(data);
    verify(data, times(1)).getName();
    assertTrue(container.size() > 0);
  }
  
  @Test
  public void testShouldKnowAmountOfContainedElements() throws Exception {
    MediaContainer container = new MediaContainer(metadataList);
    container.addMedia(data);
    container.addMedia(subContainer);
  
    assertEquals(2, container.size());
  }
  
  @Test
  public void testShouldGetMediaObjectsFromContainerByName() {
    MediaContainer container = new MediaContainer(metadataList);
    AbstractMedia data = new MediaData();
    data.setName("Foo Media");
    container.addMedia(data);
    
    assertEquals(data.getName(), container.getMedia(data.getName()).getName());
  }
  
  @Test
  public void testShouldGetNullIfMediaOfThatNameWasNotInTheContainer() {
    MediaContainer container = new MediaContainer(metadataList);
    assertNull(container.getMedia("Nothing."));
  }
  
  @Test
  public void testShouldHaveSetOfNamesOfTheContainingElements() throws Exception {
    MediaContainer container = new MediaContainer(metadataList);
    container.addMedia(data);
    container.addMedia(subContainer);

    Set<String> expectedElements = new HashSet<String>();
    expectedElements.add("LeafMedia");
    expectedElements.add("ContainerMedia");

    assertEquals(expectedElements, container.elements());
  }

}
