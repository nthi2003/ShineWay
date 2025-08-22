import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { createEntity, getEntity, reset, updateEntity } from './profile.reducer';

export const ProfileUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const profileEntity = useAppSelector(state => state.profile.entity);
  const loading = useAppSelector(state => state.profile.loading);
  const updating = useAppSelector(state => state.profile.updating);
  const updateSuccess = useAppSelector(state => state.profile.updateSuccess);

  const handleClose = () => {
    navigate(`/profile${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dob = convertDateTimeToServer(values.dob);

    const entity = {
      ...profileEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          dob: displayDefaultDateTime(),
        }
      : {
          ...profileEntity,
          dob: convertDateTimeFromServer(profileEntity.dob),
          user: profileEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="shineWayApp.profile.home.createOrEditLabel" data-cy="ProfileCreateUpdateHeading">
            Create or edit a Profile
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="profile-id" label="Id" validate={{ required: true }} /> : null}
              <ValidatedField label="First Name" id="profile-firstName" name="firstName" data-cy="firstName" type="text" />
              <ValidatedField label="Last Name" id="profile-lastName" name="lastName" data-cy="lastName" type="text" />
              <ValidatedField label="Phone" id="profile-phone" name="phone" data-cy="phone" type="text" />
              <ValidatedField label="Email" id="profile-email" name="email" data-cy="email" type="text" />
              <ValidatedField label="Dob" id="profile-dob" name="dob" data-cy="dob" type="datetime-local" placeholder="YYYY-MM-DD HH:mm" />
              <ValidatedField label="Address" id="profile-address" name="address" data-cy="address" type="text" />
              <ValidatedField label="Gender" id="profile-gender" name="gender" data-cy="gender" type="text" />
              <ValidatedField id="profile-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/profile" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ProfileUpdate;
