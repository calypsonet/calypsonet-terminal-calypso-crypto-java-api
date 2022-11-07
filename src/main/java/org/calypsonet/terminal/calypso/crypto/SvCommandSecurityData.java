/* **************************************************************************************
 * Copyright (c) 2022 Calypso Networks Association https://calypsonet.org/
 *
 * See the NOTICE file(s) distributed with this work for additional information
 * regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 ************************************************************************************** */
package org.calypsonet.terminal.calypso.crypto;

import org.calypsonet.terminal.calypso.transaction.SvAction;
import org.calypsonet.terminal.calypso.transaction.SvOperation;

/**
 * Contains the input/output data of the SV command operations (LOAD/DEBIT).
 *
 * @since 1.0.0
 */
public interface SvCommandSecurityData {
  /**
   * Gets the current {@link SvOperation}.
   * <p>
   * @return A not null {@link SvOperation}.
   * @since 1.0.0
   */
  SvOperation getSvOperation();

  /**
   * @return The current SV action.
   * @since 1.0.0
   */
  SvAction getSvAction();

  /**
   * @return The "SV Get" command header.
   * @since 1.0.0
   */
  byte[] getSvGetHeader();

  /**
   * @return The "SV Get" command output data.
   * @since 1.0.0
   */
  byte[] getSvGetData();

  /**
   * @return The "SV Load / SV Debit" command data.
   * @since 1.0.0
   */
  byte[] getSvOperationData();

  /**
   * @return The SV command security data part to be sent to the card.
   * @since 1.0.0
   */
  byte[] getSvComplementaryData();
}
