package org.kaaproject.kaa.sandbox.web.client.i18n;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/Users/faradey27/Documents/Projects/kaa/sandbox-frame/web/src/main/resources/org/kaaproject/kaa/sandbox/web/client/i18n/SandboxMessages.properties'.
 */
public interface SandboxMessages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "To make Kaa services accessible over the network, it is required that you set Kaa services host/IP to an externally reachable address. You can ignore this message now and change the host/IP later by clicking \"Change Kaa host/IP\" at the top of the Sandbox window.".
   * 
   * @return translated "To make Kaa services accessible over the network, it is required that you set Kaa services host/IP to an externally reachable address. You can ignore this message now and change the host/IP later by clicking \"Change Kaa host/IP\" at the top of the Sandbox window."
   */
  @DefaultMessage("To make Kaa services accessible over the network, it is required that you set Kaa services host/IP to an externally reachable address. You can ignore this message now and change the host/IP later by clicking \"Change Kaa host/IP\" at the top of the Sandbox window.")
  @Key("changeKaaHostDialogMessage")
  String changeKaaHostDialogMessage();

  /**
   * Translated "Kaa endpoints connect to your Kaa Sandbox by using the address built into the SDK. This address must be reachable from the network where you run your endpoints for your applications to work properly. The currently set Kaa Sandbox address is".
   * 
   * @return translated "Kaa endpoints connect to your Kaa Sandbox by using the address built into the SDK. This address must be reachable from the network where you run your endpoints for your applications to work properly. The currently set Kaa Sandbox address is"
   */
  @DefaultMessage("Kaa endpoints connect to your Kaa Sandbox by using the address built into the SDK. This address must be reachable from the network where you run your endpoints for your applications to work properly. The currently set Kaa Sandbox address is")
  @Key("changeKaaHostMessagePt1")
  String changeKaaHostMessagePt1();

  /**
   * Translated "Please use the form below to manually update your Kaa Sandbox address.".
   * 
   * @return translated "Please use the form below to manually update your Kaa Sandbox address."
   */
  @DefaultMessage("Please use the form below to manually update your Kaa Sandbox address.")
  @Key("changeKaaHostMessagePt2")
  String changeKaaHostMessagePt2();

  /**
   * Translated "{0} of {1} max characters".
   * 
   * @return translated "{0} of {1} max characters"
   */
  @DefaultMessage("{0} of {1} max characters")
  @Key("charactersLength")
  String charactersLength(String arg0,  String arg1);

  /**
   * Translated "Kaa host field can not be empty!".
   * 
   * @return translated "Kaa host field can not be empty!"
   */
  @DefaultMessage("Kaa host field can not be empty!")
  @Key("emptyKaaHostError")
  String emptyKaaHostError();

  /**
   * Translated "Powered by <b><a href=\"http://www.kaaproject.org\">Kaa IoT Application Plaform</a></b> {0}, <b>Kaa Sandbox Frame</b> {1}, <b>Sample Applications</b> {2} · <a href=\"http://jira.kaaproject.org/browse/KAA\">Report a bug</a> · <a href=\"http://docs.kaaproject.org/display/KAA\">Documentation</a>".
   * 
   * @return translated "Powered by <b><a href=\"http://www.kaaproject.org\">Kaa IoT Application Plaform</a></b> {0}, <b>Kaa Sandbox Frame</b> {1}, <b>Sample Applications</b> {2} · <a href=\"http://jira.kaaproject.org/browse/KAA\">Report a bug</a> · <a href=\"http://docs.kaaproject.org/display/KAA\">Documentation</a>"
   */
  @DefaultMessage("Powered by <b><a href=\"http://www.kaaproject.org\">Kaa IoT Application Plaform</a></b> {0}, <b>Kaa Sandbox Frame</b> {1}, <b>Sample Applications</b> {2} · <a href=\"http://jira.kaaproject.org/browse/KAA\">Report a bug</a> · <a href=\"http://docs.kaaproject.org/display/KAA\">Documentation</a>")
  @Key("footerMessage")
  String footerMessage(String arg0,  String arg1,  String arg2);

  /**
   * Translated "Invalid hostname/ip address format!".
   * 
   * @return translated "Invalid hostname/ip address format!"
   */
  @DefaultMessage("Invalid hostname/ip address format!")
  @Key("invalidKaaHostError")
  String invalidKaaHostError();

  /**
   * Translated "Here you can change the loglevel used by Kaa services.".
   * 
   * @return translated "Here you can change the loglevel used by Kaa services."
   */
  @DefaultMessage("Here you can change the loglevel used by Kaa services.")
  @Key("logsMessage")
  String logsMessage();

  /**
   * Translated "Page {0} of {1}".
   * 
   * @return translated "Page {0} of {1}"
   */
  @DefaultMessage("Page {0} of {1}")
  @Key("pagerText")
  String pagerText(String arg0,  String arg1);
}
