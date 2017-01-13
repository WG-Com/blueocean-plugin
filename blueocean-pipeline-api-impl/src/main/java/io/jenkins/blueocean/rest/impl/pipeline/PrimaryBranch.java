package io.jenkins.blueocean.rest.impl.pipeline;

import com.cloudbees.hudson.plugins.folder.AbstractFolder;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import hudson.model.Job;
import io.jenkins.blueocean.commons.ServiceException;
import jenkins.scm.api.metadata.PrimaryInstanceMetadataAction;

import javax.annotation.Nullable;
import java.util.Collection;

public final class PrimaryBranch {
    /**
     * Resolves the primary branch for a folder
     * @param folder to check within
     * @return default branch
     */
    @SuppressWarnings("unchecked")
    public static Job resolve(AbstractFolder folder) {
        Job job = Iterables.find((Collection<Job>)folder.getAllJobs(), new Predicate<Job>() {
            @Override
            public boolean apply(@Nullable Job input) {
                return input != null && input.getAction(PrimaryInstanceMetadataAction.class) != null;
            }
        }, null);
        // Kept for backward compatibility for Git SCMs that do not yet implement PrimaryInstanceMetadataAction
        if (job == null) {
            job = (Job) folder.getJob(DEFAULT_BRANCH);
        }
        if(job == null) {
            throw new ServiceException.BadRequestExpception("no default branch to favorite");
        }
        return job;
    }

    private static final String DEFAULT_BRANCH = "master";

    private PrimaryBranch() {}
}
