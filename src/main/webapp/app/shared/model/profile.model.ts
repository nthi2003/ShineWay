import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IProfile {
  id?: string;
  firstName?: string | null;
  lastName?: string | null;
  phone?: string | null;
  email?: string | null;
  dob?: dayjs.Dayjs | null;
  address?: string | null;
  gender?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IProfile> = {};
