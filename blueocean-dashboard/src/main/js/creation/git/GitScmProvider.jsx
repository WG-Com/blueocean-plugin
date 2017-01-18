import React from 'react';
import ScmProvider from '../ScmProvider';
import GitDefaultOption from './GitDefaultOption';
import GitFlowManager from './GitFlowManager';
import GitCreationApi from './GitCreationApi';
import { CredentialsApi } from '../credentials/CredentialsApi';

/**
 * Provides the impl of FlowManager and the button for starting the git flow.
 */
export default class GitScmProvider extends ScmProvider {

    constructor() {
        super();

        const createApi = new GitCreationApi();
        const credentialsApi = new CredentialsApi();

        this.manager = new GitFlowManager(createApi, credentialsApi);
    }

    getDefaultOption() {
        return <GitDefaultOption />;
    }

    getFlowManager() {
        return this.manager;
    }

}
