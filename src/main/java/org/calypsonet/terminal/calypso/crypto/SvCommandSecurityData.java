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
 * Contains the input/output data of the SV command operations (LOAD/DEBIT/UNDEBIT).
 *
 * @since 1.0.0
 */
public interface SvCommandSecurityData {
  /**
   * Sets the "SV Get" ingoing command data.
   *
   * @param svGetRequest A not empty byte array containing the apdu request data.
   * @return The object instance.
   */
  SvCommandSecurityData setSvGetRequest(byte[] svGetRequest);

  /**
   * Sets the "SV Get" outgoing command data.
   *
   * @param svGetResponse A not empty byte array containing the apdu response data.
   * @return The object instance.
   */
  SvCommandSecurityData setSvGetResponse(byte[] svGetResponse);
  /**
   * Sets the "SV Load/Debit/Undebit" outgoing command data.
   *
   * @param svCommandRequest A not empty byte array containing the apdu request data.
   * @return The object instance.
   */
  SvCommandSecurityData setSvCommandRequest(byte[] svCommandRequest);

  /**
   * Gets the serial number to be placed in the "SV Load/Debit/Undebit" command request.
   *
   * @return A not byte array containing the serial number.
   */
  byte[] getSerialNumber();

  /**
   * Gets the transaction number to be placed in the "SV Load/Debit/Undebit" command request.
   *
   * @return A not byte array containing the transaction number.
   */
  byte[] getTransactionNumber();

  /**
   * Gets the terminal challenge to be placed in the SV Load/Debit/Undebit command request.
   *
   * @return A not byte array containing the terminal challenge.
   */
  byte[] getTerminalChallenge();

  /**
   * Gets the terminal SV MAC to be placed in the "SV Load/Debit/Undebit" command request.
   *
   * @return A not byte array containing the terminal SV MAC.
   */
  byte[] getTerminalSvMac();

  /**
   * Gets the current {@link SvOperation}.
   *
   * <p>
   *
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
