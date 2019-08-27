/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.andylopresto.processors.byop;

import org.apache.nifi.util.MockFlowFile;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReverseTextTest {
    private static final Logger logger = LoggerFactory.getLogger(ReverseTextTest.class);
    private static final String MESSAGE = "This is a plaintext message. ";
    private static final String REVERSED_MESSAGE = new StringBuilder(MESSAGE).reverse().toString();

    private TestRunner testRunner;

    @Before
    public void init() {
        testRunner = TestRunners.newTestRunner(ReverseText.class);
    }

    @Test
    public void testProcessor() {
        // Arrange
        testRunner.enqueue(MESSAGE);

        // Act
        testRunner.run();
        logger.info("Ran once with " + MESSAGE);

        // Assert
        testRunner.assertAllFlowFilesTransferred(ReverseText.REL_SUCCESS, 1);
        MockFlowFile flowFile = testRunner.getFlowFilesForRelationship(ReverseText.REL_SUCCESS).get(0);
        flowFile.assertContentEquals(REVERSED_MESSAGE);
        flowFile.assertAttributeEquals("reversed", "true");
    }

}
